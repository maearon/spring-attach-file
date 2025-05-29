import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiService } from "./api.service"
import { Micropost } from "../models/micropost.model"

export interface MicropostListParams {
  page?: number
  [key: string]: any
}

export interface MicropostListResponse {
  feedItems: MicropostPage
  followers: number
  following: number
  gravatar: string
  micropost: number
  totalElements: number
}

export interface MicropostPage {
  content: Micropost[]
  totalElements: number
  totalPages?: number
  size?: number
  number?: number
}

export interface MicropostResponse {
  flash?: [string, string]
  error?: string[]
}

@Injectable({
  providedIn: "root",
})
export class MicropostService {
  constructor(private apiService: ApiService) {}

  getAll(params: MicropostListParams = {}): Observable<MicropostListResponse> {
    return this.apiService.get<MicropostListResponse>("", params)
  }

  create(formData: FormData): Observable<MicropostResponse> {
    return this.apiService.post<MicropostResponse>("/microposts", formData)
  }

  remove(id: number): Observable<MicropostResponse> {
    return this.apiService.delete<MicropostResponse>(`/microposts/${id}`)
  }
}
