import { User } from "./user";

export class Property {

    id:number;
    price: number;
    area: number;
    imageUrl: string;
    description: string;
    dateCreated: Date;
    user:User;
}
