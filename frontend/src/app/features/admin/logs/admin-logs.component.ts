import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminLogService } from '../../../services/admin-log.service';

@Component({
  selector: 'app-admin-logs',
  standalone: true,
  imports:[CommonModule],
  templateUrl:'./admin-logs.component.html',
  styleUrls:['./admin-logs.component.css']
})
export class AdminLogsComponent implements OnInit {

  logs:any[]=[];

  constructor(private service:AdminLogService){}

  ngOnInit(){
    this.service.getLogs()
      .subscribe(res => this.logs = res);
  }

}