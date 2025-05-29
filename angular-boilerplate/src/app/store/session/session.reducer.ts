import { createReducer, on } from "@ngrx/store"
import { User } from "../../models/user.model"
import * as SessionActions from "./session.actions"

export interface SessionState {
  user: User | null
  loggedIn: boolean
  loading: boolean
  error: any
}

export const initialState: SessionState = {
  user: null,
  loggedIn: false,
  loading: false,
  error: null,
}

export const sessionReducer = createReducer(
  initialState,
  on(SessionActions.login, (state) => ({
    ...state,
    loading: true,
    error: null,
  })),
  on(SessionActions.loginSuccess, (state, { user }) => ({
    ...state,
    user,
    loggedIn: true,
    loading: false,
    error: null,
  })),
  on(SessionActions.loginFailure, (state, { error }) => ({
    ...state,
    loading: false,
    error,
  })),
  on(SessionActions.logout, (state) => ({
    ...state,
    user: null,
    loggedIn: false,
  })),
  on(SessionActions.checkAuthStatus, (state) => ({
    ...state,
    loading: true,
  })),
  on(SessionActions.checkAuthStatusComplete, (state, { loggedIn }) => ({
    ...state,
    loading: false,
    loggedIn,
  })),
)
