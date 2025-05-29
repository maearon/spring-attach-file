import { Md5 } from 'ts-md5';

export function gravatarUrl(email: string, size = 80): string {
  const hash = Md5.hashStr(email.trim().toLowerCase());
  return `https://secure.gravatar.com/avatar/${hash}?s=${size}`;
}
