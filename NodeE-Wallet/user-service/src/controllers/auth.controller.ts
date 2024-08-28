import { Request, Response } from 'express';
import UserService from '../services/user.service';

class AuthController {
  static async register(req: Request, res: Response): Promise<Response> {
    const { email, password, firstName, lastName } = req.body;

    try {
      const result = await UserService.registerUser(email, password, firstName, lastName);
      return res.status(201).json(result);
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Internal server error';
      return res.status(400).json({ error: errorMessage });
    }
  }

  static async login(req: Request, res: Response): Promise<Response> {
    const { email, password } = req.body;

    try {
      const result = await UserService.loginUser(email, password);
      return res.json(result);
    } catch (error) {
      const errorMessage = error instanceof Error ? error.message : 'Internal server error';
      return res.status(401).json({ error: errorMessage });
    }
  }
}

export default AuthController;
