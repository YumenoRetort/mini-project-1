import express from 'express';
import AuthController from '../controllers/auth.controller';
import createValidationMiddleware from '../middleware/validation.middleware'; // Corrected path
import authenticateJWT from '../middleware/auth.middleware'; // Corrected path
import validator from '../utils/validator'; // Adjust import path if necessary

const router = express.Router();

// Apply validation middleware to the registration route using validator.validateUser
router.post('/register', createValidationMiddleware(validator.validateUser), AuthController.register);

// Apply validation middleware to the login route using validator.validateLogin
router.post('/login', createValidationMiddleware(validator.validateLogin), AuthController.login);

// // Example of a protected route using the authenticateJWT middleware
// router.get('/profile', authenticateJWT, AuthController.getProfile);

export default router;
