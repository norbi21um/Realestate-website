import { Property } from './property';

export class User {
  id: number;
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  phoneNumber: string;
  properties: Property[] = [];
  optimalTimeToPost: number;
  monthlyReach: number;
}
//Még kell egy ilyen:
//private Set<Role> roles = new HashSet<>();
