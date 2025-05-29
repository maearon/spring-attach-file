import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiService } from "./api.service"

export interface CreateRelationshipParams {
  followed_id: string
}

export interface CreateRelationshipResponse {
  follow: boolean
}

export interface DestroyRelationshipResponse {
  unfollow: boolean
}

@Injectable({
  providedIn: "root",
})
export class RelationshipService {
  constructor(private apiService: ApiService) {}

  follow(params: CreateRelationshipParams): Observable<CreateRelationshipResponse> {
    return this.apiService.post<CreateRelationshipResponse>(`/relationships/${params.followed_id}/follow`, params)
  }

  unfollow(id: string): Observable<DestroyRelationshipResponse> {
    return this.apiService.delete<DestroyRelationshipResponse>(`/relationships/${id}/unfollow`)
  }
}
