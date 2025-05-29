import { User as UserMicropost } from './user.model';

interface MicropostBase {
  readonly id: number
  content: string
  gravatar?: string
  image: string
  imageUrls: string[]
  size: number
  createdAt: string
  readonly user_id: string
  title?: string
  description?: string
  videoId?: string
  channelTitle?: string
}

// Trường hợp có `user`, không có `user_name`
interface MicropostWithUser extends MicropostBase {
  user: UserMicropost
  user_name?: never
}

// Trường hợp không có `user`, có thể có `user_name`
interface MicropostWithUserName extends MicropostBase {
  user?: undefined
  user_name?: string
}

export type Micropost = MicropostWithUser | MicropostWithUserName;
