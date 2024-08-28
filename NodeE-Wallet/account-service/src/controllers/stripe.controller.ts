// import { Request, Response } from 'express';
// import StripeService from '../services/stripe.service';
// import validator from '../utils/validator';
// import logger from '../utils/logger';

// interface AuthenticatedRequest extends Request {
//   user?: {
//     stripeCustomerId: string;
//   };
// }

// const initStripeService = (req: Request): StripeService => {
//   const stripeSecretKey = req.headers['stripe-secret-key'] as string;
//   return new StripeService(stripeSecretKey);
// };

// export const createPaymentMethod = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const { type, card } = req.body;

//     if (type !== 'card' || !card) {
//       return res.status(400).json({ error: 'Invalid payment method details' });
//     }

//     const stripeService = initStripeService(req);
//     const paymentMethod = await stripeService.createPaymentMethod(type, card);
//     const customerId = req.user?.stripeCustomerId;  // Assuming this is stored with the user
//     if (customerId) {
//       await stripeService.attachPaymentMethodToCustomer(paymentMethod.id, customerId);
//     }

//     res.json({ paymentMethod });
//   } catch (error) {
//     logger.error('Error creating payment method:', error);
//     res.status(500).json({ error: error.message });
//   }
// };

// export const getPaymentMethods = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const customerId = req.user?.stripeCustomerId;  // Assuming this is stored with the user
//     const stripeService = initStripeService(req);
//     if (customerId) {
//       const paymentMethods = await stripeService.listCustomerPaymentMethods(customerId);
//       res.json({ paymentMethods });
//     } else {
//       res.status(400).json({ error: 'Customer ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error getting payment methods:', error);
//     res.status(500).json({ error: error.message });
//   }
// };

// export const createPaymentIntent = async (req: AuthenticatedRequest, res: Response) => {
//   try {
//     const { error } = validator.validateAmount(req.body.amount);
//     if (error) return res.status(400).json({ error: error.details[0].message });

//     const { amount } = req.body;
//     const customerId = req.user?.stripeCustomerId;  // Assuming this is stored with the user

//     const stripeService = initStripeService(req);
//     if (customerId) {
//       const paymentIntent = await stripeService.createPaymentIntent(amount, 'usd', customerId);
//       res.json({ clientSecret: paymentIntent.client_secret });
//     } else {
//       res.status(400).json({ error: 'Customer ID not found.' });
//     }
//   } catch (error) {
//     logger.error('Error creating payment intent:', error);
//     res.status(500).json({ error: error.message });
//   }
// };
