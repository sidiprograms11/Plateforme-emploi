import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration } from 'chart.js';
import { AdminLiveService } from '../../../core/services/admin-live.service';

import {
Chart,
LineController,
LineElement,
BarController,
BarElement,
PointElement,
LinearScale,
CategoryScale,
Title,
Tooltip,
Legend,
Filler
} from 'chart.js';

Chart.register(
LineController,
LineElement,
BarController,
BarElement,
PointElement,
LinearScale,
CategoryScale,
Title,
Tooltip,
Legend,
Filler
);

@Component({
selector: 'app-admin-dashboard',
standalone: true,
imports: [CommonModule, BaseChartDirective],
templateUrl: './admin-dashboard.component.html',
styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

offers = 0;
applications = 0;
identities = 0;

events: string[] = [];

constructor(private live: AdminLiveService){}

chartData: ChartConfiguration<'line'>['data'] = {
labels: ['Lun','Mar','Mer','Jeu','Ven','Sam','Dim'],
datasets: [
{
data: [5,8,4,10,12,6,9],
label: 'Documents créés',
borderColor: '#3b82f6',
backgroundColor: 'rgba(59,130,246,0.2)',
tension: 0.4,
fill: true
}
]
};

chartOptions: ChartConfiguration<'line'>['options'] = {
responsive: true,
plugins:{
legend:{
display:true
}
}
};

ngOnInit(){

this.offers = 3;
this.applications = 12;
this.identities = 2;

this.live.connect((msg: string)=>{

console.log("Admin event:", msg);

this.events.unshift(msg);

if(this.events.length > 10){
this.events.pop();
}

});

}

}