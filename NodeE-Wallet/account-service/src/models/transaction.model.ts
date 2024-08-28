import mongoose, { Document, Schema } from 'mongoose';

interface ITransaction extends Document {
  type: 'deposit' | 'withdraw' | 'transfer';
  amount: number;
  currency?: string;
  fromWallet?: mongoose.Types.ObjectId;
  toWallet?: mongoose.Types.ObjectId;
  status?: 'pending' | 'completed' | 'failed';
  stripePaymentIntentId?: string;
  metadata?: any; // You may want to define a more specific type if known
  createdAt?: Date;
}

const transactionSchema = new Schema<ITransaction>({
  type: {
    type: String,
    enum: ['deposit', 'withdraw', 'transfer'],
    required: true,
  },
  amount: {
    type: Number,
    required: true,
    min: 0,
  },
  currency: {
    type: String,
    default: 'USD',
  },
  fromWallet: {
    type: Schema.Types.ObjectId,
    ref: 'Wallet',
    required: function() { return this.type === 'transfer'; },
  },
  toWallet: {
    type: Schema.Types.ObjectId,
    ref: 'Wallet',
    required: function() { return this.type === 'transfer' || this.type === 'deposit'; },
  },
  status: {
    type: String,
    enum: ['pending', 'completed', 'failed'],
    default: 'pending',
  },
  stripePaymentIntentId: {
    type: String,
  },
  metadata: {
    type: Schema.Types.Mixed,
  },
  createdAt: {
    type: Date,
    default: Date.now,
  },
});

const Transaction = mongoose.model<ITransaction>('Transaction', transactionSchema);

export default Transaction;
