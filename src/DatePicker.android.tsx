import React, { useEffect, useState } from 'react';
import { StyleProp, StyleSheet, View, ViewStyle } from 'react-native';
import WheelPicker from './WheelPicker.android';

import {
    getDateInMonth,
    increaseDateByDays,
    increaseMonthByMonths,
    increaseYearByYears,
    pickerDateArray,
    pickerMonthArray,
    pickerYearArray,
    DEFAULT_END_YEAR,
    DEFAULT_START_YEAR,
} from './Utils';

type Props = {
    initDate: Date;
    onDateSelected: (date: Date) => void;
    maximumDate?: Date;
    minimumDate?: Date;
    selectedItemTextColor?: string;
    selectedItemTextSize?: number;
    selectedItemTextFontFamily: string;
    itemTextColor?: string;
    itemTextSize?: number;
    itemTextFontFamily: string;
    indicatorColor?: string;
    hideIndicator?: boolean;
    indicatorWidth?: number;
    backgroundColor?: string;
    style?: StyleProp<ViewStyle>;
};

const DatePicker: React.FC<Props> = (props) => {
    const { initDate, maximumDate, minimumDate, onDateSelected } = props;
    const maxValue = maximumDate || new Date(`${DEFAULT_END_YEAR}`);
    const minValue = minimumDate || new Date(`${DEFAULT_START_YEAR}`);
    const [initDayIndex, setInitDayIndex] = useState(0);
    const [initMonthIndex, setInitMonthIndex] = useState(0);
    const [initYearIndex, setInitYearIndex] = useState(0);
    const [selectedDate, setSelectedDate] = useState(initDate);

    useEffect(() => {
        const selectedDateFinal = getDateSelected(selectedDate);
        setSelectedDate(selectedDateFinal);
        setInitDayIndex(selectedDateFinal.getDate() - 1);
        setInitMonthIndex(selectedDateFinal.getMonth());
        setInitYearIndex(selectedDateFinal.getFullYear() - minValue.getFullYear());
        if (onDateSelected) onDateSelected(selectedDateFinal);
    }, [selectedDate]);

    const onDaySelected = (position: number) => {
        let newSelectedDate = selectedDate;
        const month = newSelectedDate.getMonth();
        const year = newSelectedDate.getFullYear();
        newSelectedDate = increaseDateByDays(minValue, position);
        newSelectedDate.setFullYear(year);
        newSelectedDate.setMonth(month);
        if (newSelectedDate.getDate() !== selectedDate.getDate()) {
            setInitDayIndex(position);
            setSelectedDate(newSelectedDate);
        }
    };

    const onMonthSelected = (position: number) => {
        let newSelectedDate = selectedDate;
        const date = newSelectedDate.getDate();
        const year = newSelectedDate.getFullYear();
        newSelectedDate = increaseMonthByMonths(minValue, position);
        newSelectedDate.setFullYear(year);
        newSelectedDate.setDate(getDateInMonth(date, newSelectedDate));
        if (newSelectedDate.getMonth() !== selectedDate.getMonth()) {
            setInitMonthIndex(position);
            setSelectedDate(newSelectedDate);
        }
    };

    const onYearSelected = (position: number) => {
        let newSelectedDate = selectedDate;
        const month = newSelectedDate.getMonth();
        const date = newSelectedDate.getDate();
        newSelectedDate = increaseYearByYears(minValue, position);
        newSelectedDate.setMonth(month);
        newSelectedDate.setDate(getDateInMonth(date, newSelectedDate));
        if (newSelectedDate.getFullYear() !== selectedDate.getFullYear()) {
            setInitYearIndex(position);
            setSelectedDate(newSelectedDate);
        }
    };

    const getDateSelected = (selectDate: Date): Date => {
        if (selectDate.getTime() >= minValue.getTime() && selectDate.getTime() <= maxValue.getTime()) {
            return selectDate;
        } else if (selectDate.getTime() > maxValue.getTime()) {
            return maxValue;
        } else {
            return minValue;
        }
    };

    return (
        <View style={styles.container}>
            <WheelPicker
                style={styles.dateWheelPicker}
                {...props}
                isCyclic
                data={pickerMonthArray(minValue)}
                onItemSelected={onMonthSelected}
                selectedItem={initMonthIndex}
            />
            <WheelPicker
                style={styles.dateWheelPicker}
                {...props}
                isCyclic
                data={pickerDateArray(selectedDate)}
                onItemSelected={onDaySelected}
                selectedItem={initDayIndex}
            />
            <WheelPicker
                style={styles.dateWheelPicker}
                {...props}
                data={pickerYearArray(minValue, maxValue)}
                onItemSelected={onYearSelected}
                selectedItem={initYearIndex}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        alignItems: 'center',
        flexDirection: 'row',
    },
    wheelPicker: {
        height: 150,
        flex: 1,
    },
    dateWheelPicker: {
        height: 150,
        flex: 3,
    },
});

export default DatePicker;
