import { Pipe, PipeTransform } from '@angular/core';
import { Md5 } from 'ts-md5';

@Pipe({ name: 'gravatar' })
export class GravatarPipe implements PipeTransform {
  transform(email: string, size = 80): string {
    const hash = Md5.hashStr(email.trim().toLowerCase());
    return `https://secure.gravatar.com/avatar/${hash}?s=${size}`;
  }
}
