import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiService } from "./api.service"
import { User, UserShow, UserEdit } from "../models/user.model"
import { Micropost } from "../models/micropost.model"

export interface UserListParams {
  page?: number
  [key: string]: any
}

export interface UserListResponse {
  content: User[]
  totalElements: number
}

export interface UserShowResponse {
  user: UserShow
  id_relationships?: number
  microposts: Micropost[]
  total_count: number
}

export interface UserEditResponse {
  user: UserEdit
  gravatar: string
  flash?: [string, string]
}

export interface UserUpdateParams {
  name: string
  email: string
  password: string
  password_confirmation: string
}

export interface UserUpdateResponse {
  flash_success?: [string, string]
  error?: string[]
}

export interface UserCreateParams {
  name: string
  email: string
  password: string
  password_confirmation: string
}

export interface UserCreateResponse {
  user?: User
  flash?: [string, string]
  error?: string[]
  status?: number
  message?: string
  errors?: {
    [key: string]: string[]
  }
}

export interface FollowResponse {
  users: User[]
  totalElements: number
  user: {
    id: string
    name: string
    followers: number
    following: number
    gravatar: string
    micropost: number
  }
}

@Injectable({
  providedIn: "root",
})
export class UserService {
  constructor(private apiService: ApiService) {}

  getUsers(params: UserListParams = {}): Observable<UserListResponse> {
    return this.apiService.get<UserListResponse>("/users", params)
  }

  getUser(id: string, params: UserListParams = {}): Observable<UserShowResponse> {
    return this.apiService.get<UserShowResponse>(`/users/${id}`, params)
  }

  editUser(id: string): Observable<UserEditResponse> {
    return this.apiService.get<UserEditResponse>(`/users/${id}/edit`)
  }

  updateUser(id: string, params: { user: UserUpdateParams }): Observable<UserUpdateResponse> {
    return this.apiService.patch<UserUpdateResponse>(`/users/${id}`, params)
  }

  createUser(params: { user: UserCreateParams }): Observable<UserCreateResponse> {
    return this.apiService.post<UserCreateResponse>("/signup", params)
  }

  deleteUser(id: string): Observable<{ flash?: [string, string] }> {
    return this.apiService.delete<{ flash?: [string, string] }>(`/users/${id}`)
  }

  getFollowers(id: string, page: number): Observable<FollowResponse> {
    return this.apiService.get<FollowResponse>(`/users/${id}/followers`, { page })
  }

  getFollowing(id: string, page: number): Observable<FollowResponse> {
    return this.apiService.get<FollowResponse>(`/users/${id}/following`, { page })
  }
}
