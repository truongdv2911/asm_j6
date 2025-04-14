import { productItemApi } from "./productType";

export interface OrderType {
    id_user: number;
    fullname: string;
    sdt: string;
    address: string;
    total: number;
    cartItems : {id_product: number, quantity: number}[];
}