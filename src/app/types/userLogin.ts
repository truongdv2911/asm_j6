import { IsPhoneNumber, IsString, IsNotEmpty, IsDate } from 'class-validator';

export class userLogin {
    @IsPhoneNumber('VN')
    sdt: string;

    @IsString()
    @IsNotEmpty()
    password: string;

    constructor(data: any) {
        this.sdt = data.sdt;
        this.password = data.password;
    }
}

    