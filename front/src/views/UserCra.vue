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
				:filter-method="publicFilterProject"
				row-key="name">
				<template v-slot:top-left>
					<p>{{title}}
						<span class="bold">{{user.name }} {{user.firstName | toUpperCase}} </span>- {{currentMonthBtn}} {{currentYearBtn}} -
						<span> Open days seized: {{totalQuartersInMonth}}/{{openDays}} </span>
						<div class="q-gutter-md row">
							<q-select
							outlined
							v-model="selectYear"
							:options="[
							{label: lastYear, value: lastYear, description: 'Last year', icon: 'map'},
							{label: currentYear, value: currentYear, description: 'Current year', icon: 'map'}
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
						outlined
						v-model="selectMonth"
						:options="months"
						@input="publicOnSelectionChanged($event)"
						label="Month"
						dense
						:options-dense="denseOpts">
						<template v-slot:prepend>
							<q-icon name="event" />
						</template>
					</q-select>
					<q-input outlined autofocus borderless dense
					debounce="300"
					v-model="filter"
					placeholder="Search">
					<template v-slot:append>
						<q-icon name="search" />
					</template>
				</q-input>
				<q-btn color="primary" class="btn" :to="{name:'userList'}" label="Return"/>
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
						debounce="1000"
						@validate='(val) =>validate(val)'
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
			currentMonthBtn: '',
			currentYearBtn : '',
			months: [],
			lastYear: moment().year()-1,
			currentYear: moment().year(),
			pagination: {
				rowsPerPage: 10 //  0 for all
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
		this.$store.dispatch('searchAllTeamHisProject');
		this.$store.dispatch('searchOneUser', this.$route.params.id);
	},

	beforeMount() {
		// Initialize the table with the current month
		let day  = moment().format('MM/YYYY');
		this.daysArrayByMonthCal(day)
		// List of months
		this.publicListOfMonth();
	},

	updated(){
		// init colors
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
			// this.controlQuarters();
		},
		quarters : function(){
			this.controlQuarters();
		}
	},
	// Utils
	methods: {
		controlQuarters(){

			for (let index = 0; index < this.quarters.length; index++) {
				for (let i = 0; i < this.schedule.length; i++) {

					if (this.schedule[i].format('YYYY-MM-DD') === this.quarters[index].date) {

						if (this.quarters[index].quarter > 1) {

							this.schedule[i].classStatus = this.classStatus.NOT_OK;
						} else if (this.quarters[index].quarter == 1) {

							this.schedule[i].classStatus = this.classStatus.OK;
						} else if (this.quarters[index].quarter > 0 && this.quarters[index].quarter < 1) {
							this.schedule[i].classStatus = this.classStatus.PROGRESS;
						}else {
							this.schedule[i].classStatus = this.classStatus.INACTIVE;
						}
					}
				}
			}
		},
		publicOnSelectionChanged(numberMonth){
			this.daysArrayByMonthCal(numberMonth.number +'/'+this.selectYear);
		},
		publicFilterProject (rows, terms) {
			const lowerTerms = terms ? terms.toLowerCase() : '';
			return rows.filter((row) => {
				return row.nameProject.toLowerCase().includes(lowerTerms) || row.nameTeam.toLowerCase().includes(lowerTerms);
			});
		},
		validate(val){
			//validate value

			if (val < 0 || val > 1) {
				this.errorValue = true;
				this.errorMessageValue = 'The value must be between 0 and 1!';
				return false;
			}

		},
		privateAllRecordsPerMonth(date){
			// Date of the first day of the current month
			let startMonth = moment(date,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
			this.startMonth = startMonth;
			// Date of the last day of the month
			let endMonth = moment(date,'MM/YYYY').endOf('month').format('YYYY-MM-DD');
			this.endMonth = endMonth;
			// Search all quarter/date
			this.$store.dispatch('searchQuartersByDate',{startDate: startMonth, endDate: endMonth, id: this.$route.params.id});

			this.controlQuarters();
		},
		publicListOfMonth(){
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
		privateOpenDaysPerMonth(){
			let openDays = this.schedule.length;
			for (let i = 0; i < this.schedule.length; i++) {
				if (this.schedule[i].holiday) {
					openDays = openDays-1;
				}
			}
			this.openDays = openDays;
		},
		// we manually trigger it (this is not needed if we
		// don't skip Ajax calls hijacking)0.5
		async OnQuarterChange(quarter, row, props) {
			if (quarter == '') {
				quarter = 0
			}

			// Convert date string to Date Object
			let day = moment(moment(row), 'DD/MM/YYYY');
			if (quarter < 0 || quarter > 1 ) {
				this.showNotif();

			}	else if (quarter > 0 && quarter <= 1) {

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
				// request
				await this.$store.dispatch('getAddRecord', addRecord);

			}	else if (quarter == 0) {
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
				// request
				await this.$store.dispatch('getRemoveRecord', removeRecord);
			}

			 this.daysArrayByMonthCal(day);
		},
		// ajax Bar
		OnQuarterChangeBar(){
			// const bar = this.$refs.bar
			this.$refs.bar.start();
			this.timer = setTimeout(() => {
				if (this.$refs.bar) {
					this.$refs.bar.stop()
				}
			}, Math.random());
		},
		showNotif () {
			this.$q.notify({
				color: 'negative',
				textColor: "white",
				position: 'center',
				message : 'Oops incorrect value',
				timeout: Math.random() * 3000,
			})
		},
		daysArrayByMonthCal(date) {

				this.loading=true;
				//get the number of records per month
				this.privateAllRecordsPerMonth(date);
				// year recovery to calculate holidays
				let year = moment(date,'MM/YYYY').format('YYYY');
				// retrieving the holiday table
				let arrDaysHolidays = holiday.getHolidays(year);

				// Retrieving the current month/year to display it
				this.currentMonthBtn = moment(date,'MM/YYYY').format('MMMM');
				this.currentYearBtn = year;

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
				this.privateOpenDaysPerMonth();

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
input {
	text-align: center;
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
