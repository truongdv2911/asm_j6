import {Pipe, PipeTransform} from '@angular/core';
@Pipe({
    name: 'pipe',
    standalone: true,
})

export class CurrencyPipe implements PipeTransform {
    transform(value: number): string{
        return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
    }
}