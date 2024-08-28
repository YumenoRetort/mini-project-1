import express, { Application, Request, Response, NextFunction } from 'express';
import cors from 'cors';
import routes from './routes';
import errorMiddleware from './middleware/error.middleware';
import mongoose from 'mongoose';
import dotenv from 'dotenv';

// Load environment variables from .env file
dotenv.config();

// Define the interface for process.env to avoid errors when using environment variables
declare global {
  namespace NodeJS {
    interface ProcessEnv {
      MONGODB_URI: string;
      PORT?: string;
    }
  }
}

// Connect to MongoDB
mongoose.connect(process.env.MONGODB_URI!, {
    // No need for useNewUrlParser, useUnifiedTopology, useCreateIndex, or useFindAndModify
  })
  .then(() => console.log('Connected to MongoDB'))
  .catch(err => console.error('MongoDB connection error:', err));

// Initialize express application
const app: Application = express();

// Middleware
app.use(cors());
app.use(express.json());

// Use routes
app.use('/api', routes);

// Error handling middleware
app.use((err: Error, req: Request, res: Response, next: NextFunction) => {
  errorMiddleware(err, req, res, next);
});

// Set the port and start the server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));

export default app;
