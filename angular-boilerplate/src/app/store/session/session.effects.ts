import { Injectable, inject } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { of } from "rxjs";
import { catchError, map, switchMap } from "rxjs/operators";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { AuthService } from "../../services/auth.service";
import * as SessionActions from "./session.actions";

@Injectable()
export class SessionEffects {
  // ✅ dùng inject() thay cho constructor
  private actions$ = inject(Actions);
  private authService = inject(AuthService);
  private router = inject(Router);
  private toastr = inject(ToastrService);

  login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(SessionActions.login),
      switchMap(({ email, password, rememberMe }) =>
        this.authService.login({ email, password, remember_me: rememberMe }).pipe(
          map((response) => {
            this.toastr.success("Logged in successfully");
            this.router.navigate(["/"]);
            return SessionActions.loginSuccess({ user: response.user });
          }),
          catchError((error) => {
            let errorMessage = "Login failed";
            if (error?.error?.message) {
              errorMessage = error.error.message;
            }
            this.toastr.error(errorMessage);
            return of(SessionActions.loginFailure({ error }));
          })
        )
      )
    )
  );
}
