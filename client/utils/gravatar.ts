import md5 from "blueimp-md5"

export const getGravatarUrl = (email: string | null, size = 50): string => {
  const validEmail = email ? email.trim().toLowerCase() : "test@example.com"
  const hash = md5(validEmail)
  return `https://secure.gravatar.com/avatar/${hash}?s=${size}`
}
