import { Injectable } from "@angular/core"
import { Observable } from "rxjs"
import { ApiService } from "./api.service"
import { User } from "../models/user.model"

export interface ResendActivationEmailParams {
  resend_activation_email: {
    email: string
  }
}

export interface ActivationResponse {
  user_id?: string
  flash?: [string, string]
  error?: string[]
}

export interface ActivationUpdateResponse {
  user?: User
  jwt?: string
  token?: string
  flash: [string, string]
}

@Injectable({
  providedIn: "root",
})
export class AccountActivationService {
  constructor(private apiService: ApiService) {}

  resendActivationEmail(params: ResendActivationEmailParams): Observable<ActivationResponse> {
    return this.apiService.post<ActivationResponse>("/account_activations", params)
  }

  activateAccount(token: string, email: string): Observable<ActivationUpdateResponse> {
    return this.apiService.patch<ActivationUpdateResponse>(`/account_activations/${token}`, { email })
  }
}
