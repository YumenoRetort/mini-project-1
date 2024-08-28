import { Request, Response, NextFunction } from 'express';
import Joi, { ValidationResult } from 'joi';

// Updated function to accept a validation function
const createValidationMiddleware = (validationFn: (data: any) => ValidationResult) => {
  return (req: Request, res: Response, next: NextFunction) => {
    const { error } = validationFn(req.body);
    if (error) {
      return res.status(400).json({ error: error.details[0].message });
    }
    next();
  };
};

export default createValidationMiddleware;
