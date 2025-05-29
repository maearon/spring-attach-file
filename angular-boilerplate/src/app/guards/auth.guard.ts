import { Injectable } from "@angular/core"
import { CanActivate, Router, UrlTree } from "@angular/router"
import { Observable } from "rxjs"
import { AuthService } from "../services/auth.service"
import { ToastrService } from "ngx-toastr"

@Injectable({
  providedIn: "root",
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router,
    private toastr: ToastrService,
  ) {}

  canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if (this.authService.isAuthenticated()) {
      return true
    }

    this.toastr.error("Please log in to access this page")
    return this.router.createUrlTree(["/login"])
  }
}
