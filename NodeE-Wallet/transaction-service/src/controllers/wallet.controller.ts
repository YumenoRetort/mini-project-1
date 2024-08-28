// import { Request, Response } from 'express';
// import WalletService from '../services/wallet.service';
// import validator from '../utils/validator';
// import logger from '../utils/logger';

// interface AuthenticatedRequest extends Request {
//   user?: {
//     id: string;
//     email: string;
//   };
// }

// export const createWallet = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const { error } = validator.validateWalletCreation(req.body);
//     if (error) return res.status(400).json({ error: error.details[0].message });

//     const { initialBalance } = req.body;
//     const userId = req.user?.id;

//     if (userId) {
//       const result = await WalletService.createWallet(userId, req.user.email, initialBalance);
//       res.status(201).json(result);
//     } else {
//       res.status(400).json({ error: 'User ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error creating wallet:', error);
//     res.status(500).json({ error: error.message });
//   }
// };

// export const deposit = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const { error } = validator.validateTransaction({ ...req.body, type: 'deposit' });
//     if (error) return res.status(400).json({ error: error.details[0].message });

//     const { amount, paymentMethodId } = req.body;
//     const userId = req.user?.id;

//     if (userId) {
//       const result = await WalletService.deposit(userId, amount, paymentMethodId);
//       res.json(result);
//     } else {
//       res.status(400).json({ error: 'User ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error depositing funds:', error);
//     res.status(500).json({ error: error.message });
//   }
// };

// export const transfer = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const { error } = validator.validateTransaction({ ...req.body, type: 'transfer' });
//     if (error) return res.status(400).json({ error: error.details[0].message });

//     const { toUserId, amount } = req.body;
//     const fromUserId = req.user?.id;

//     if (fromUserId) {
//       const result = await WalletService.transfer(fromUserId, toUserId, amount);
//       res.json(result);
//     } else {
//       res.status(400).json({ error: 'User ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error transferring funds:', error);
//     res.status(500).json({ error: error.message });
//   }
// };

// export const withdraw = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const { error } = validator.validateTransaction({ ...req.body, type: 'withdraw' });
//     if (error) return res.status(400).json({ error: error.details[0].message });

//     const { amount, destinationAccount } = req.body;
//     const userId = req.user?.id;

//     if (userId) {
//       const result = await WalletService.withdraw(userId, amount, destinationAccount);
//       res.json(result);
//     } else {
//       res.status(400).json({ error: 'User ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error withdrawing funds:', error);
//     res.status(500).json({ error: error.message });
//   }
// };

// export const getBalance = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const userId = req.user?.id;

//     if (userId) {
//       const result = await WalletService.getBalance(userId);
//       res.json(result);
//     } else {
//       res.status(400).json({ error: 'User ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error getting balance:', error);
//     res.status(500).json({ error: error.message });
//   }
// };

// export const getTransactionHistory = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const userId = req.user?.id;

//     if (userId) {
//       const result = await WalletService.getTransactionHistory(userId);
//       res.json(result);
//     } else {
//       res.status(400).json({ error: 'User ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error getting transaction history:', error);
//     res.status(500).json({ error: error.message });
//   }
// };
