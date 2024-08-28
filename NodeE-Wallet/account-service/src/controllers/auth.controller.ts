import { Request, Response } from 'express';
import User from '../models/user.model'; 
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';

class AuthController {
  static async register(req: Request, res: Response): Promise<Response> {
    const { email, password, firstName, lastName } = req.body;

    try {
      // Check if the user already exists
      const existingUser = await User.findOne({ email });
      if (existingUser) {
        return res.status(400).json({ error: 'Email already in use' });
      }

      // Hash the password
      // const hashedPassword = await bcrypt.hash(password, 12);

      // Create a new user
      const newUser = new User({
        email,
        password,
        firstName,
        lastName
      });

      await newUser.save();

      return res.status(201).json({ message: 'User registered successfully' });
    } catch (error) {
      return res.status(500).json({ error: 'Internal server error' });
    }
  }

  static async login(req: Request, res: Response): Promise<Response> {
    const { email, password } = req.body;

    try {
      // Find the user
      const user = await User.findOne({ email });
      if (!user) {
        return res.status(401).json({ error: 'Invalid credentials - email' });
      }

      // Check the password
      const isMatch = await user.checkPassword(password);
      if (!isMatch) {
        return res.status(401).json({ error: 'Invalid credentials - password' });
      }

      // Generate a token
      const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET as string, { expiresIn: '1h' });

      return res.json({ token });
    } catch (error) {
      return res.status(500).json({ error: 'Internal server error' });
    }
  }
}

export default AuthController;
