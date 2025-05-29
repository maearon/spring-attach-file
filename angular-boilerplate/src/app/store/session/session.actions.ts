import { createAction, props } from "@ngrx/store"
import { User } from "../../models/user.model"

export const login = createAction("[Auth] Login", props<{ email: string; password: string; rememberMe: boolean }>())

export const loginSuccess = createAction("[Auth] Login Success", props<{ user: User }>())

export const loginFailure = createAction("[Auth] Login Failure", props<{ error: any }>())

export const logout = createAction("[Auth] Logout")

export const checkAuthStatus = createAction("[Auth] Check Auth Status")

export const checkAuthStatusComplete = createAction("[Auth] Check Auth Status Complete", props<{ loggedIn: boolean }>())
