export interface User {
  readonly id: string
  name: string
  username: string
  email: string
  gravatar?: string
  size?: number
  following?: number
  followers?: number
  current_user_following_user?: boolean
  admin?: boolean
  activated?: boolean
}

export interface UserShow extends User {
  following: number
  followers: number
  current_user_following_user: boolean
}

export interface UserEdit {
  name: string
  email: string
}
