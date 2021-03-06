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
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [initDayIndex, setInitDayIndex] = useState(0);
    const [initMonthIndex, setInitMonthIndex] = useState(0);
    const [initYearIndex, setInitYearIndex] = useState(0);
    const [maxValue, setMaxValue] = useState(new Date(`${DEFAULT_END_YEAR}`));
    const [minValue, setMinValue] = useState(new Date(`${DEFAULT_START_YEAR}`));

    useEffect(() => {
        const selectedDate = initDate ? initDate : new Date();
        const initDayIndex = selectedDate.getDate() - 1;
        const initMonthIndex = selectedDate.getMonth();
        const initYearIndex = selectedDate.getFullYear() - minValue.getFullYear();
        setSelectedDate(selectedDate);
        setInitDayIndex(initDayIndex);
        setInitMonthIndex(initMonthIndex);
        setInitYearIndex(initYearIndex);
        if (maximumDate) setMaxValue(maximumDate);
        if (minimumDate) setMinValue(minimumDate);
    }, []);

    const onDaySelected = (position: number) => {
        let newSelectedDate = selectedDate;
        const month = newSelectedDate.getMonth();
        const year = newSelectedDate.getFullYear();
        newSelectedDate = increaseDateByDays(minValue, position);
        newSelectedDate.setFullYear(year);
        newSelectedDate.setMonth(month);
        handleDateSelected(newSelectedDate);
        setInitDayIndex(newSelectedDate.getDate() - 1);
    };

    const onMonthSelected = (position: number) => {
        let newSelectedDate = selectedDate;
        const date = newSelectedDate.getDate();
        const year = newSelectedDate.getFullYear();
        newSelectedDate = increaseMonthByMonths(minValue, position);
        newSelectedDate.setFullYear(year);
        const initDayIndex = getDateInMonth(date, newSelectedDate) - 1;
        newSelectedDate.setDate(getDateInMonth(date, newSelectedDate));
        handleDateSelected(newSelectedDate);
        setInitDayIndex(initDayIndex);
    };

    const onYearSelected = (position: number) => {
        let newSelectedDate = selectedDate;
        const month = newSelectedDate.getMonth();
        const date = newSelectedDate.getDate();
        newSelectedDate = increaseYearByYears(minValue, position);
        newSelectedDate.setMonth(month);
        const initDayIndex = getDateInMonth(date, newSelectedDate) - 1;
        newSelectedDate.setDate(getDateInMonth(date, newSelectedDate));
        handleDateSelected(newSelectedDate);
        setInitDayIndex(initDayIndex);
    };

    const handleDateSelected = (selectedDate: Date) => {
        setSelectedDate(selectedDate);
        if (onDateSelected) onDateSelected(selectedDate);
    };

    return (
        <View style={styles.container}>
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
                isCyclic
                data={pickerMonthArray(minValue)}
                onItemSelected={onMonthSelected}
                selectedItem={initMonthIndex}
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
    dateWheelPicker: {
        height: 150,
        flex: 3,
    },
});

export default DatePicker;
