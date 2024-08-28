import User from '../models/user.model';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';

class UserService {
  static async registerUser(email: string, password: string, firstName: string, lastName: string) {
    // Check if the user already exists
    const existingUser = await User.findOne({ email });
    if (existingUser) {
      throw new Error('Email already in use');
    }

    // Create a new user
    const newUser = new User({
      email,
      password,
      firstName,
      lastName
    });

    await newUser.save();
    return { message: 'User registered successfully' };
  }

  static async loginUser(email: string, password: string) {
    // Find the user
    const user = await User.findOne({ email });
    if (!user) {
      throw new Error('Invalid credentials - email');
    }

    // Check the password
    const isMatch = await user.checkPassword(password);
    if (!isMatch) {
      throw new Error('Invalid credentials - password');
    }

    // Generate a token
    const token = jwt.sign({ id: user._id }, process.env.JWT_SECRET as string, { expiresIn: '1h' });

    return { token };
  }
}

export default UserService;
