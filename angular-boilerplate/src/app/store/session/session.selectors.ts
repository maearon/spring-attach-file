import { createFeatureSelector, createSelector } from "@ngrx/store"
import { SessionState } from "./session.reducer"

export const selectSessionState = createFeatureSelector<SessionState>("session")

export const selectUser = createSelector(selectSessionState, (state: SessionState) => state.user)

export const selectLoggedIn = createSelector(selectSessionState, (state: SessionState) => state.loggedIn)

export const selectLoading = createSelector(selectSessionState, (state: SessionState) => state.loading)

export const selectError = createSelector(selectSessionState, (state: SessionState) => state.error)
