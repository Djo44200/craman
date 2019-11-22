import moment from 'moment';
export default {

  getHolidays (an) {
    // an => YYYY
    // Calculation of holiday dates
    //https://codes-sources.commentcamarche.net/source/16245-calcul-des-jours-feries
    let newYear = new Date(an, "00", "01")
    let laborDay = new Date(an, "04", "01") //FeteTravail
    let Victoiry1945 = new Date(an, "04", "08") //Victoire1945
    let bationalHoliday = new Date(an,"06", "14") //feteNationale
    let Assumption = new Date(an, "07", "15") //Assomption
    let Toussaint = new Date(an, "10", "01")
    let Armistice = new Date(an, "10", "11")
    let Christmas = new Date(an, "11", "25") //Noel

    let G = an%19
    let C = Math.floor(an/100)
    let H = (C - Math.floor(C/4) - Math.floor((8*C+13)/25) + 19*G + 15)%30
    let I = H - Math.floor(H/28)*(1 - Math.floor(H/28)*Math.floor(29/(H + 1))*Math.floor((21 - G)/11))
    let J = (an*1 + Math.floor(an/4) + I + 2 - C + Math.floor(C/4))%7
    let L = I - J
    let monthEaster = 3 + Math.floor((L + 40)/44) //MoisPaques
    let dayEaster = L + 28 - 31*Math.floor(monthEaster/4) //JourPaques
    let easter = new Date(an, monthEaster-1, dayEaster) //Paques
    let mondayEaster = new Date(an, monthEaster-1, dayEaster+1)
    let Ascension = new Date(an, monthEaster-1, dayEaster+39)
    let Pentecote = new Date(an, monthEaster-1, dayEaster+49)
    let mondayPentecote = new Date(an, monthEaster-1, dayEaster+50) //LundiPentecote
    // Format Dates at this table
    const arrayHolidays = ([
      newYear = moment(newYear).format('DD/MM/YY'),
      easter = moment(easter).format('DD/MM/YY'),
      mondayEaster = moment(mondayEaster).format('DD/MM/YY'),
      laborDay = moment(laborDay).format('DD/MM/YY'),
      Victoiry1945 = moment(Victoiry1945).format('DD/MM/YY'),
      Ascension = moment(Ascension).format('DD/MM/YY'),
      Pentecote = moment(Pentecote).format('DD/MM/YY'),
      mondayPentecote = moment(mondayPentecote).format('DD/MM/YY'),
      bationalHoliday = moment(bationalHoliday).format('DD/MM/YY'),
      Assumption = moment(Assumption).format('DD/MM/YY'),
      Toussaint = moment(Toussaint).format('DD/MM/YY'),
      Armistice = moment(Armistice).format('DD/MM/YY'),
      Christmas = moment(Christmas).format('DD/MM/YY'),
    ]);
    return arrayHolidays;
  }

}
