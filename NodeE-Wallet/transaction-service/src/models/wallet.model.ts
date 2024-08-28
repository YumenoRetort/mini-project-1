import mongoose, { Document, Schema } from 'mongoose';

interface IWallet extends Document {
  user: mongoose.Types.ObjectId;
  balance: number;
  currency?: string;
  stripeCustomerId: string;
  createdAt?: Date;
  updatedAt?: Date;
}

const walletSchema = new Schema<IWallet>({
  user: {
    type: Schema.Types.ObjectId,
    ref: 'User',
    required: true,
  },
  balance: {
    type: Number,
    default: 0,
    min: 0,
  },
  currency: {
    type: String,
    default: 'USD',
  },
  stripeCustomerId: {
    type: String,
    required: true,
  },
  createdAt: {
    type: Date,
    default: Date.now,
  },
  updatedAt: {
    type: Date,
    default: Date.now,
  },
});

// Update the updatedAt field before saving
walletSchema.pre<IWallet>('save', function(next) {
  this.updatedAt = new Date(); // Ensure it's a Date object
  next();
});

const Wallet = mongoose.model<IWallet>('Wallet', walletSchema);

export default Wallet;
