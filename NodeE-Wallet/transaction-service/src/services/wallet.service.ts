// import { Document, Model, model, Schema } from 'mongoose';
// import StripeService from './stripe.service';
// import User from '../models/user.model';
// import Transaction from '../models/transaction.model';

// interface Wallet {
//   balance: number;
//   stripeCustomerId: string;
// }

// interface UserDocument extends Document {
//   email: string;
//   password: string;
//   firstName: string;
//   lastName: string;
//   wallet?: Wallet;
//   save(): Promise<this>;
// }

// interface TransactionDocument extends Document {
//   type: 'deposit' | 'withdraw' | 'transfer';
//   userId: string;
//   amount: number;
//   fromUserId?: string;
//   toUserId?: string;
//   destinationAccount?: string;
//   timestamp: Date;
//   save(): Promise<this>;
// }

// class WalletService {
//   private stripeService: typeof StripeService;

//   constructor() {
//     this.stripeService = StripeService;
//   }

//   async createWallet(userId: string, email: string): Promise<{ success: boolean; wallet: Wallet }> {
//     const user = await User.findById(userId).exec();
//     if (!user) {
//       throw new Error('User not found');
//     }
//     if (user.wallet) {
//       throw new Error('Wallet already exists for this user');
//     }
    
//     const customer = await this.stripeService.createCustomer(email);
//     user.wallet = { balance: 0, stripeCustomerId: customer.id };
//     await user.save();
    
//     return { success: true, wallet: user.wallet };
//   }

//   async deposit(userId: string, amount: number, paymentMethodId: string): Promise<{ success: boolean; transaction: TransactionDocument; newBalance: number }> {
//     const user = await User.findById(userId).exec();
//     if (!user || !user.wallet) {
//       throw new Error('Wallet not found');
//     }
    
//     try {
//       const paymentIntent = await this.stripeService.createPaymentIntent(amount, 'usd', user.wallet.stripeCustomerId);
//       const confirmedPayment = await this.stripeService.confirmPaymentIntent(paymentIntent.id, paymentMethodId);
      
//       if (confirmedPayment.status === 'succeeded') {
//         user.wallet.balance += amount;
//         await user.save();
        
//         const transaction = new Transaction({
//           type: 'deposit',
//           userId,
//           amount,
//           timestamp: new Date()
//         });
//         await transaction.save();
        
//         return { success: true, transaction, newBalance: user.wallet.balance };
//       } else {
//         throw new Error('Payment failed');
//       }
//     } catch (error) {
//       console.error('Deposit failed:', error);
//       throw new Error('Deposit failed: ' + error.message);
//     }
//   }

//   async transfer(fromUserId: string, toUserId: string, amount: number): Promise<{ success: boolean; transaction: TransactionDocument; fromBalance: number; toBalance: number }> {
//     const fromUser = await User.findById(fromUserId).exec();
//     const toUser = await User.findById(toUserId).exec();
    
//     if (!fromUser || !fromUser.wallet || !toUser || !toUser.wallet) {
//       throw new Error('One or both wallets not found');
//     }
    
//     if (fromUser.wallet.balance < amount) {
//       throw new Error('Insufficient funds');
//     }
    
//     fromUser.wallet.balance -= amount;
//     toUser.wallet.balance += amount;
    
//     await fromUser.save();
//     await toUser.save();
    
//     const transaction = new Transaction({
//       type: 'transfer',
//       fromUserId,
//       toUserId,
//       amount,
//       timestamp: new Date()
//     });
//     await transaction.save();
    
//     return { 
//       success: true, 
//       transaction, 
//       fromBalance: fromUser.wallet.balance, 
//       toBalance: toUser.wallet.balance 
//     };
//   }

//   async withdraw(userId: string, amount: number, destinationAccount: string): Promise<{ success: boolean; transaction: TransactionDocument; newBalance: number }> {
//     const user = await User.findById(userId).exec();
//     if (!user || !user.wallet) {
//       throw new Error('Wallet not found');
//     }
    
//     if (user.wallet.balance < amount) {
//       throw new Error('Insufficient funds');
//     }
    
//     try {
//       await this.stripeService.createPayout(amount, 'usd', destinationAccount);
      
//       user.wallet.balance -= amount;
//       await user.save();
      
//       const transaction = new Transaction({
//         type: 'withdraw',
//         userId,
//         amount,
//         destinationAccount,
//         timestamp: new Date()
//       });
//       await transaction.save();
      
//       return { success: true, transaction, newBalance: user.wallet.balance };
//     } catch (error) {
//       console.error('Withdrawal failed:', error);
//       throw new Error('Withdrawal failed: ' + error.message);
//     }
//   }

//   async getBalance(userId: string): Promise<{ success: boolean; balance: number }> {
//     const user = await User.findById(userId).exec();
//     if (!user || !user.wallet) {
//       throw new Error('Wallet not found');
//     }
    
//     return { success: true, balance: user.wallet.balance };
//   }

//   async getTransactionHistory(userId: string): Promise<{ success: boolean; transactions: TransactionDocument[] }> {
//     const transactions = await Transaction.find({
//       $or: [
//         { userId: userId },
//         { fromUserId: userId },
//         { toUserId: userId }
//       ]
//     }).sort({ timestamp: -1 }).exec();
    
//     return { success: true, transactions };
//   }
// }

// export default new WalletService();
