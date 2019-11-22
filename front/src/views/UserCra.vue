<template>
	<div class="showUserCra" v-if="user.name">
		<q-ajax-bar
		ref="bar"
		position="top"
		color="blue"
		size="2.5px"
		skip-hijack
		/>
		<form>
			<div class="q-pa-md">
				<div class="center">
					<q-banner v-if="totalQuartersInMonth > openDays && openDays != 0" rounded inline-actions class="text-white banner">
						{{errorMessageOpenDays}}
					</q-banner>
				</div>
				<q-table
				dense
				:data="data"
				bordered
				:loading="loading"
				separator="cell"
				:columns="columns"
				:pagination.sync="pagination"
				:filter="filter"
				:filter-method="filterProject"
				row-key="name">
				<template v-slot:top-left>
					<p>{{title}}
						<span class="bold">{{user.name }} {{user.firstName | toUpperCase}} </span></p>
						<p>{{currentMonth}} {{currentYearChange}} -
						Open days seized: {{totalQuartersInMonth}}/{{openDays}}</p>
						<div class="q-gutter-md row">
							<q-select
							class="col-md col-4"
							outlined
							v-model="selectYear"
							:options="[
							{label: lastYear, value: lastYear, description: 'Last year'},
							{label: currentYear, value: currentYear, description: 'Current year'}
							]"
							label="Year"
							dense
							:options-dense="denseOpts"
							>
							<template v-slot:prepend>
								<q-icon name="event" />
							</template>
						</q-select>
						<q-select
						class="col-md col-6"
						outlined
						v-model="selectMonth"
						:options="months"
						@input="onSelectionChanged($event)"
						label="Month"
						dense
						:options-dense="denseOpts">
						<template v-slot:prepend>
							<q-icon name="event" />
						</template>
					</q-select>
					<q-input outlined autofocus borderless dense
					class="col-md col-6"
					debounce="300"
					v-model="filter"
					placeholder="Search">
					<template v-slot:append>
						<q-icon name="search" />
					</template>
				</q-input>
				<q-btn color="primary" class="btn col-md col-4" :to="{name:'userList'}" label="Return"/>
			</div>
		</template>
		<template v-slot:body="props" :props="props">
			<q-tr :props="props">
				<q-td class="line col-auto" key="desc" >
					{{ props.row.nameTeam | toUpperCase }} - {{ props.row.nameProject | toUpperCase}}
				</q-td>
				<q-td v-for="(day, name) in schedule"
				:key="name + day.timestamp"
				v-bind:class="'color'+day.classStatus">
				<div v-if="!day.holiday">
					<q-input @input="(val) => OnQuarterChange(val, day.timestamp, props)"
						debounce="500"
						:error="errorValue" :error-message="errorMessageValue"
						type="text" pattern="^\d*(\.\d{0,2})?$"
						step="0.25" min="0" max="1" dense
						v-model="props.row[day.timestamp]">
					</q-input>
				</div>
				<div v-else class="line">
					<q-input
					disable
					type="text" pattern="^\d*(\.\d{0,2})?$"
					step="0.25" min="0" max="1" dense
					v-model="props.row[day.timestamp]">
				</q-input>
			</div>
		</q-td>
	</q-tr>

</template>
</q-table>
</div>
</form>
</div>
</template>

<script>
import moment from 'moment';
import holiday from '@/services/Days'
import { mapState } from 'vuex';
import _ from 'lodash';
export default {
	components: {},
	filters:{
		toUpperCase: function (value){
			if (!value) return ''
			value = value.toString()
			return value.toUpperCase();
		}
	},
	// Object used for view to update his content
	data: function() {
		return {
			classStatus:{
				'INACTIVE': 'Basic',
				'OK': 'OK',
				'PROGRESS': 'Progress',
				'NOT_OK': 'NOK',
			},
			loading: false,
			startMonth:'',
			endMonth:'',
			quartersByDate: [],
			selectMonth: moment().month(moment().month()).format('MMMM'),
			dense: false,
			denseOpts: false,
			selectYear: moment().year(),
			errorMessageOpenDays: "Please check your days.",
			data: [],
			errorValue: false,
			errorMessageValue: '',
			filter: '',
			openDays: 0,
			title: 'Activity Report of ' ,
			currentMonth: '',
			months: [],
			currentYearChange : '',
			lastYear: moment().year()-1,
			currentYear: moment().year(),
			pagination: {
				rowsPerPage: 15//  0 for all
			},
			text: '',
			schedule: [],
			columns: [
				{
					name: 'desc',
					label: 'Projects',
					align: 'left',
				},
			],
		};
	},
	computed: {
		...mapState({
			user: 'user',
			teams: 'teams',
			records: 'records',
			quarters: 'listQuarters',
		}),
		totalQuartersInMonth() {
			return _.sumBy(this.records, 'quarter');
		},
	},
	beforeCreate() {
		// Loading all the teams and associated projects
		this.$store.dispatch('searchAllTeamHisProject');
		//Searching the current user
		this.$store.dispatch('searchOneUser', this.$route.params.id);
	},

	beforeMount() {
		// Initialize the table with the current month and year
		let day  = moment().format('MM/YYYY');
		this.daysArrayByMonthCal(day)
		// List of months for this select
		this.listOfMonth();
	},

	updated(){
		// initialize colors for the table
		this.controlQuarters();
	},

	watch: {
		//val is the new value of the state when teamList change !
		records: function (val) {
			const data = [];
			const teams = this.teams;
			const records = val;
			// init from teams
			for (let index = 0; teams && index < teams.length; index++) {
				const team = teams[index];
				const element = {
					'nameTeam': team.name,
					'idTeam': team.idTeam,
					'idProject': team.idProject,
					'nameProject': team.nameProject,
				}
				for (let i = 0; records && i < records.length; i++) {
					const record = records[i];
					if (record.idTeam === team.idTeam && record.idProject === team.idProject) {
						element[record.timestamp] = record.quarter;
					}
				}
				data.push(element);
			}
			this.data = data;
		},
		//Whenever a quarter changes
		quarters : function(){
			this.controlQuarters();
		}
	},
	// Utils
	methods: {
		controlQuarters(){
			// Change of classes
			for (let index = 0; index < this.quarters.length; index++) {
				for (let i = 0; i < this.schedule.length; i++) {
					if (this.schedule[i].format('YYYY-MM-DD') === this.quarters[index].date) {
						if (this.quarters[index].quarter > 1) {
							this.schedule[i].classStatus = this.classStatus.NOT_OK;
						} else if (this.quarters[index].quarter == 1) {
							this.schedule[i].classStatus = this.classStatus.OK;
						} else if (this.quarters[index].quarter > 0 && this.quarters[index].quarter < 1) {
							this.schedule[i].classStatus = this.classStatus.PROGRESS;
						} else {
							this.schedule[i].classStatus = this.classStatus.INACTIVE;
						}
					}
				}
			}
		},
		onSelectionChanged(numberMonth){
			// format => MM/YYYY
			this.daysArrayByMonthCal(numberMonth.number +'/'+ this.selectYear.value);
		},
		filterProject (rows, terms) {
			//Search by projects
			const lowerTerms = terms ? terms.toLowerCase() : '';
			return rows.filter((row) => {
				return row.nameProject.toLowerCase().includes(lowerTerms) || row.nameTeam.toLowerCase().includes(lowerTerms);
			});
		},
		async allRecordsPerMonth(date){
			// Date of the first day of the current month
			let startMonth = moment(date,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
			this.startMonth = startMonth;
			// Date of the last day of the month
			let endMonth = moment(date,'MM/YYYY').endOf('month').format('YYYY-MM-DD');
			this.endMonth = endMonth;
			// Search all quarter/date
			await this.$store.dispatch('searchQuartersByDate',{startDate: startMonth, endDate: endMonth, id: this.$route.params.id});
			// initialize colors for the table
			this.controlQuarters();
		},
		listOfMonth(){
			for (let index = 0; index < 12; index++){
				const column = {
					'label': moment.months(index),
					'value': index+1,
					'name': moment.months(index),
					'number': index+1,
				}
				this.months.push(column);
			}
		},
		openDaysPerMonth(){

			// Calculating the number of working days in the month
			let openDays = this.schedule.length;
			for (let i = 0; i < this.schedule.length; i++) {
				if (this.schedule[i].holiday) {
					openDays = openDays-1;
				}
			}
			this.openDays = openDays;
		},

		async OnQuarterChange(quarter, row, props) {
			//Function to save quarter

			if (quarter == '') {
				quarter = 0
			}

			// Convert date string to Date Object
			let day = moment(moment(row), 'DD/MM/YYYY');
			if (quarter < 0 || quarter > 1 ) {
				this.showNotif();

			}	else if (quarter > 0 && quarter <= 1) {
				//building the record object
				let addRecord = {
					quarter: parseFloat(quarter, 2),
					date: day,
					timestamp: row,
					idUser: this.user.idUser,
					idProject: props.row.idProject,
					idTeam: props.row.idTeam,
					nameProject: props.row.nameProject,
					nameTeam: props.row.nameTeam,
				}
				this.quarterChangeBar();
				// request
				await this.$store.dispatch('getAddRecord', addRecord);

			}	else if (quarter == 0) {
				//building the record object
				let removeRecord = {
					quarter: parseFloat(quarter, 2),
					date: day,
					timestamp: row,
					idUser: this.user.idUser,
					idProject: props.row.idProject,
					idTeam: props.row.idTeam,
					nameProject: props.row.nameProject,
					nameTeam: props.row.nameTeam,
				}
				this.quarterChangeBar();
				// request
				await this.$store.dispatch('getRemoveRecord', removeRecord);

			}else {
				this.showNotif();
			}
			await this.daysArrayByMonthCal(day);

		},
		// ajax Bar
		quarterChangeBar(){
			// const bar = this.$refs.bar
			this.$refs.bar.start();
			if (this.$refs.bar) {
				this.$refs.bar.stop()
			}
		},
		showNotif () {
			this.$q.notify({
				color: 'negative',
				textColor: "white",
				position: 'center',
				message : 'Oops incorrect value. Please enter a value between 0 and 1.',
				timeout: Math.random() * 1000,
			})
		},
		daysArrayByMonthCal(date) {

			this.loading=true;
			//get the number of records per month
			this.allRecordsPerMonth(date);
			// year recovery to calculate holidays
			let year = moment(date,'MM/YYYY').format('YYYY');
			// retrieving the holiday table
			let arrDaysHolidays = holiday.getHolidays(year);

			// Retrieving the current month/year to display it
			this.currentMonth = moment(date,'MM/YYYY').format('MMMM');
			this.currentYearChange = year;

			//resets the tables
			this.schedule = [];
			this.columns = [{
				name: 'desc',
				label: 'Projects',
				align: 'left',
			}];

			// Get the number of days in the month
			let daysInMonth = moment(date,'MM/YYYY').daysInMonth();
			const arrDays = [];
			for(let i = 0; i < daysInMonth; i++) {
				const day = moment(date,'MM/YYYY').date(i+1);
				arrDays.push(day);
			}
			this.schedule = arrDays;
			//Incrementing the date in a table
			for (let index = 0; index < this.schedule.length; index++) {
				const time = this.schedule[index];
				const column = {
					'name': time.startOf('day').valueOf(),
					'align': 'center',
					'label': time.format('DD/MM'),
					'field': time.startOf('day').valueOf(),
					'sortable': false,
				}
				// Timestamp of the date so that the value of the row/date is unique
				this.schedule[index].timestamp = time.startOf('day').valueOf();
				this.schedule[index].classStatus = this.classStatus.INACTIVE;
				for (var i = 0; i < arrDaysHolidays.length; i++) {
					// search if dat is holiday or weekend
					if (time.format('DD/MM/YY') === arrDaysHolidays[i]
					|| time.format('dddd') === 'Sunday'
					|| time.format('dddd') === 'Saturday' ) {
						column.holiday = true;
						this.schedule[index].holiday=true;
					}
				}
				this.columns.push(column);
			}
			// open days
			this.openDaysPerMonth();

			this.loading=false;
		},
	},
}
</script>
<style scoped>

.q-table td {
	padding: 0px !important;
}
.q-field--with-bottom{
	padding: 0px !important;
}
.colorProgress {
	background-color: #F0BE87;
}
.colorBasic {
	background-color: #FFF;
}
.colorNOK {
	background-color: #DD5B4CD9;
}
.colorOK{
	background-color: #b1ffb1;
}
.opendays{
	color: red;
}
.banner{
	background-color: #FB6554;
}
.bold {
	font-weight: bold;
}
.hide{
	display: none;
}

.center{
	text-align: center !important;
}
.line{
	background-color:#F5F5DC;
}

.btn{
	width: 100px;
}
</style>
