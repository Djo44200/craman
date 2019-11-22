<template>
  <div id="timeSpent">
    <h5>Time spent by project</h5>
    <div class="q-pa-md">
      <q-table
        :data="data"
        bordered
        flat
        title="'From '+startTrimester+' at '+ endTrimester"
        separator="horizontal"
        :pagination.sync="pagination"
        :columns="columns"
        :filter="filter"
        :filter-method="filterProject"
        row-key="name">
      <template v-slot:top-left>
        <p class="bold">From {{startTrimester | formatDate }} at {{endTrimester | formatDate}}</p>

        <div class="q-gutter-md row">
          <q-select
            class="col-md-2 col-4"
            rounded outlined
            label="Year"
            v-model="selectYear"
            :options="[
              {label: lastYear, value: lastYear},
              {label: currentYear, value: currentYear}
            ]"
          />
          <q-select
          class="col-md-3 col-5"
          rounded outlined
          v-model="trimester"
          :options="trimesterOptions"
          @input="selectionTrimester($event,selectYear)"
          label="Trimester"
          />
          <q-input class="col-md-4 col-10" outlined borderless dense debounce="300" v-model="filter" placeholder="Search">
            <template v-slot:append>
              <q-icon name="search" />
            </template>
          </q-input>
          <q-btn class="col-md-3 col-5" :loading="downloadLoading" label="Export Excel" color="primary" @click="handleDownload"/>
          <q-btn color="primary" class="col-md-3 col-5" :to="{name:'userList'}" label="Return"/>
        </div>
      </template>
      <template v-slot:body="props" :props="props">
        <q-tr :props="props" >
          <q-td class="line" key="desc" :props="props">
            {{ props.row.name }}
          </q-td>
          <q-td class="line" key="desc1">
            {{ props.row.quarters }}
          </q-td>
        </q-tr>
      </template>
    </q-table>
  </div>
  <router-view/>
</div>
<!-- </div> -->
</template>

<script>
import moment from 'moment';
import { mapState } from 'vuex';
import _ from 'lodash';
export default {
  filters: {
    formatDate: function(date) {
      if (date) {
        return moment(String(date)).format('MMMM YYYY')
      }
    },
  },
  data () {
    return {
      downloadLoading: false,
      pagination: {
        rowsPerPage: 10 //  0 for all
      },
      filter: '',
      columns: [
        {
          name: 'desc',
          required: true,
          label: 'Team/Project',
          align: 'left',
          field: row => row.name,
          format: val => `${val}`,
          sortable: true
        },
        { name: 'quarters', align: 'left', label: 'Day(s)', field: 'quarters', sortable: true },
      ],
      data: [],
      startTrimester: null,
      endTrimester: null,
      trimester: 0,
      trimesterOptions: [],
      selectYear: moment().year(),
      lastYear: moment().year()-1,
      currentYear: moment().year(),
    }
  },
  computed: mapState({
    records:'records',
    totalQuartersInTrimester() {
			return _.sumBy(this.records, 'quarter');
		},
  }),
  mounted(){
    // Table of trimester
    this.createTrimester();
    // init trimester
    this.initTableTrimester();
  },
  watch: {
    records: function (val) {
      const data = [];
      const records = val;
      for (var index = 0; records && index < records.length; index++) {
        const record = records[index];
        const element = {
          'name': record.nameTeam + ' / '+record.nameProject,
          'quarters': record.quarter,
        }
        data.push(element);
      }
      this.data = data;
      //total all quarters
      this.records[0].allQuarters = _.sumBy(this.records, 'quarter');
    },
  },
  methods: {
    handleDownload() {
      // Generate excel
      this.downloadLoading = true
      import('@/services/Export2Excel').then(excel => {
        const tHeader = ['Trimester :'+ this.startTrimester + ' - ' + this.endTrimester,'Team','Project', 'Days','Total all days' ]
        const filterVal = ['','nameTeam', 'nameProject','quarter','allQuarters']
        const list = this.records
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: 'TimeSpent '+ moment().format('YYYY-MM-DD HH:mm:ss') ,
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
          return v[j]
      }))
    },
    initTableTrimester(){
      let year = this.currentYear;
      let day = moment().format('YYYY-MM-DD');
      let trimester = 0;

      // Definition of trimesters
      let startJanuary =  moment('01/'+year,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
      let endMarch = moment('03/'+year,'MM/YYYY').endOf('month').format('YYYY-MM-DD');

      let startApril = moment('04/'+year,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
      let endJune = moment('06/'+year,'MM/YYYY').endOf('month').format('YYYY-MM-DD');

      let startJuly = moment('07/'+year,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
      let endSeptember = moment('09/'+year,'MM/YYYY').endOf('month').format('YYYY-MM-DD');

      if (day>startJanuary && day<endMarch) {
        trimester = 1
        this.selectionTrimester(trimester,year);

      }else if (day>startApril && day<endJune) {
        trimester = 2
        this.selectionTrimester(trimester,year);

      }else if (day>startJuly && day<endSeptember) {
        trimester = 3
        this.selectionTrimester(trimester,year);

      }else {
        trimester = 4
        this.selectionTrimester(trimester,year);
      }
    },
    filterProject (rows, terms) {
      const lowerTerms = terms ? terms.toLowerCase() : '';
      return rows.filter((row) => {
        return row.name.toLowerCase().includes(lowerTerms);
      });
    },
    selectionTrimester(trim,year){
      let trimester = 0;

      if (!trim.value) {
        trimester = trim;
      } else {
        trimester = trim.value;
      }

      if (!year.value) {
        // Initialize year
        year = moment().year();
      } else {
        year = year.value;
      }

      if (trimester === 1) {
        let january = '01/'+year;
        let march = '03/'+year;
        this.startTrimester = moment(january,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
        this.endTrimester = moment(march,'MM/YYYY').endOf('month').format('YYYY-MM-DD');
        this.trimester = this.trimesterOptions[0];
      } else if (trimester === 2) {
        let april = '04/'+year;
        let june = '06/'+year;
        this.startTrimester = moment(april,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
        this.endTrimester = moment(june,'MM/YYYY').endOf('month').format('YYYY-MM-DD');
        this.trimester = this.trimesterOptions[1];
      } else if (trimester === 3) {
        let july = '07/'+year;
        let september = '09/'+year;
        this.startTrimester = moment(july,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
        this.endTrimester = moment(september,'MM/YYYY').endOf('month').format('YYYY-MM-DD');
        this.trimester = this.trimesterOptions[2];

      }else if (trimester === 4) {
        let october = '10/'+year;
        let december = '12/'+year;
        this.startTrimester = moment(october,'MM/YYYY').startOf('month').format('YYYY-MM-DD');
        this.endTrimester = moment(december,'MM/YYYY').endOf('month').format('YYYY-MM-DD');
        this.trimester = this.trimesterOptions[3];
      }
      // Search all quarter/Trimester
      this.$store.dispatch('searchQuartersTrimester',{startDate: this.startTrimester, endDate: this.endTrimester});
    },
    createTrimester(){
      // Create Trimesters
      for (let index = 1; index < 5; index++){
        this.trimesterOptions.push({
          'label': "Trimester " + index,
          'value': index,
        });
      }
    },
  },
}
</script>

<style scoped>
h5 {
  text-align: center;
}
.bold {
  font-weight: bold;
}

</style>
