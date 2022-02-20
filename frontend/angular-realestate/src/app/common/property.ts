import { User } from "./user";

export class Property {

    id:number;
    address:string;
    price: number;
    area: number;
    imageUrl: string;
    description: string;
    dateCreated: Date;
    user:User;
}
