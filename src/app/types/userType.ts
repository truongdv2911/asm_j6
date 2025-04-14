
import { IsPhoneNumber, IsString, IsNotEmpty, IsDate } from 'class-validator';

export class userApi {
    @IsString()
    full_name: string;

    @IsPhoneNumber('VN')
    sdt: string;

    @IsString()
    address: string;

    @IsString()
    password: string

    @IsDate()
    date_of_birth: Date;


    facebook_id: number = 0;
    google_id: number = 0;
    id_role: number = 2;

    constructor(username: string, sdt: string, address: string, password: string, dateBirth: Date, faceID: string, googleID: string, role_id: number) {
        this.full_name = username;
        this.sdt = sdt;
        this.address = address;
        this.password = password;
        this.date_of_birth = dateBirth;
        this.facebook_id = Number(faceID) || 0;
        this.google_id =  Number(googleID) || 0;
        this.id_role = role_id;
    }
}