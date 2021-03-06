import moment from 'moment';

const MONTH = 12;
export const DEFAULT_START_YEAR = 1900;
export const DEFAULT_END_YEAR = 2100;

export const increaseDateByDays = (date: Date, numOfDays: number): Date => {
    const nextDate = new Date(date.valueOf());
    nextDate.setDate(nextDate.getDate() + numOfDays);
    return nextDate;
};

export const increaseMonthByMonths = (date: Date, numOfMonth: number): Date => {
    const nextMonth = new Date(date.valueOf());
    nextMonth.setMonth(nextMonth.getMonth() + numOfMonth);
    return nextMonth;
};

export const increaseYearByYears = (date: Date, numOfYear: number): Date => {
    const nextYear = new Date(date.valueOf());
    nextYear.setFullYear(nextYear.getFullYear() + numOfYear);
    return nextYear;
};

export const pickerDateArray = (date: Date): string[] => {
    const arr = [];
    const startDate = moment(date)
        .clone()
        .startOf('month');
    const currentDate = moment(date);
    for (let i = 0; i < currentDate.daysInMonth(); ++i) {
        arr.push(startDate.format('DD'));
        startDate.add(1, 'd');
    }
    return arr;
};

export const pickerMonthArray = (minValue: Date): string[] => {
    const currentDate = moment(minValue, 'YYYY');
    const arr = [];
    for (let i = 0; i < MONTH; ++i) {
        arr.push(currentDate.format('MM'));
        currentDate.add(1, 'M');
    }
    return arr;
};

export const pickerYearArray = (minValue: Date, maxValue: Date): string[] => {
    const max = Math.abs(maxValue.getFullYear() - minValue.getFullYear());
    const arr = [];
    const startYear = moment(minValue, 'YYYY');
    for (let i = 0; i <= max; ++i) {
        arr.push(startYear.format('YYYY'));
        startYear.add(1, 'y');
    }
    return arr;
};

export const getDateInMonth = (date: number, selectedDate: Date): number => {
    const countDays = moment(selectedDate).daysInMonth();
    if (date > countDays) {
        return countDays;
    }
    return date;
};
