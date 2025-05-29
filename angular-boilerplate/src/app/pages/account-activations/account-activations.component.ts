import { Component,  OnInit } from "@angular/core"
import { CommonModule } from "@angular/common"
import { ActivatedRoute, Router } from "@angular/router"
import { ToastrService } from "ngx-toastr"
import { AccountActivationService } from "../../services/account-activation.service"

@Component({
  selector: "app-account-activations",
  standalone: true,
  imports: [CommonModule],
  template: `
    <div style="text-align: center; margin-top: 20px">
      <h1>Activating your account...</h1>
      <p>Please wait while we process your activation.</p>
    </div>
  `,
})
export class AccountActivationsComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private accountActivationService: AccountActivationService,
  ) {}

  ngOnInit(): void {
    const token = this.route.snapshot.paramMap.get("token")
    const email = this.route.snapshot.queryParamMap.get("email")

    if (!token || !email) {
      this.toastr.error("Invalid activation link")
      this.router.navigate(["/"])
      return
    }

    this.accountActivationService.activateAccount(token, email).subscribe({
      next: (response) => {
        this.toastr.success("The account has been activated. Please log in.")
        setTimeout(() => {
          this.router.navigate(["/login"])
        }, 3000)
      },
      error: (error) => {
        console.error("Activation Error:", error)
        this.toastr.error("Account activation failed. Please try again.")
        this.router.navigate(["/"])
      },
    })
  }
}
