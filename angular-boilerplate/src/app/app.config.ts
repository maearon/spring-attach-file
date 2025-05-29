import { ApplicationConfig } from "@angular/core"
import { provideRouter, withComponentInputBinding } from "@angular/router"
import { provideHttpClient, withInterceptors } from "@angular/common/http"
import { provideAnimations } from "@angular/platform-browser/animations"
import { provideToastr } from "ngx-toastr"
import { provideStore } from "@ngrx/store"
import { provideEffects } from "@ngrx/effects"
import { provideStoreDevtools } from "@ngrx/store-devtools"

import { routes } from "./app.routes"
import { authInterceptor } from "./interceptors/auth.interceptor"
import { sessionReducer } from "./store/session/session.reducer"
import { SessionEffects } from "./store/session/session.effects"

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes, withComponentInputBinding()),
    provideHttpClient(withInterceptors([authInterceptor])),
    provideAnimations(),
    provideToastr({
      timeOut: 5000,
      positionClass: "toast-top-center",
      preventDuplicates: true,
    }),
    provideStore({
      session: sessionReducer,
    }),
    provideEffects([SessionEffects]),
    provideStoreDevtools({
      maxAge: 25,
      logOnly: false,
    }),
  ],
}
