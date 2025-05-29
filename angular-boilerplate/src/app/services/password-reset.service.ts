import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiService } from "./api.service"

export interface PasswordResetCreateParams {
  password_reset: {
    email: string
  }
}

export interface PasswordResetCreateResponse {
  flash: [string, string]
}

export interface PasswordResetUpdateParams {
  email: string
  user: {
    password: string
    password_confirmation: string
  }
}

export interface PasswordResetUpdateResponse {
  user_id?: string
  flash?: [string, string]
  error?: string[]
}

@Injectable({
  providedIn: "root",
})
export class PasswordResetService {
  constructor(private apiService: ApiService) {}

  requestPasswordReset(params: PasswordResetCreateParams): Observable<PasswordResetCreateResponse> {
    return this.apiService.post<PasswordResetCreateResponse>("/password_resets", params)
  }

  resetPassword(token: string, params: PasswordResetUpdateParams): Observable<PasswordResetUpdateResponse> {
    return this.apiService.patch<PasswordResetUpdateResponse>(`/password_resets/${token}`, params)
  }
}
