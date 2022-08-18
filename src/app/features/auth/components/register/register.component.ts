import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(private authService: AuthService) { }

  public ngOnInit(): void {
    this.authService.register({login: 'test@test.com', password: 'test!31'}).subscribe(s => console.log(s));
  }

}
