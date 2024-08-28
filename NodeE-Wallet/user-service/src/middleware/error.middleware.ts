import { Request, Response, NextFunction } from 'express';
import logger from '../utils/logger';

// Define the types for error handling
interface ErrorWithStack extends Error {
  stack?: string;
  code?: number;
  keyValue?: { [key: string]: any };
  errors?: { [key: string]: { message: string } };
}

const errorHandler = (err: ErrorWithStack, req: Request, res: Response, next: NextFunction) => {
  logger.error(err.stack);

  // Mongoose validation error
  if (err.name === 'ValidationError') {
    const errors = Object.values(err.errors!).map((error) => error.message);
    return res.status(400).json({ error: errors });
  }

  // Mongoose duplicate key error
  if (err.code === 11000) {
    const field = Object.keys(err.keyValue!)[0];
    return res.status(400).json({ error: `${field} already exists.` });
  }

  // JWT authentication error
  if (err.name === 'UnauthorizedError') {
    return res.status(401).json({ error: 'Invalid token' });
  }

  // Default to 500 server error
  res.status(500).json({ error: 'Internal Server Error' });
};

export default errorHandler;
