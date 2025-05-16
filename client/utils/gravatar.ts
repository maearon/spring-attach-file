import md5 from "blueimp-md5"

export const getGravatarUrl = (email: string, size = 50): string => {
  const hash = md5(email.trim().toLowerCase())
  return `https://secure.gravatar.com/avatar/${hash}?s=${size}`
}