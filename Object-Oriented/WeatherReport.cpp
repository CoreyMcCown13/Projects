#include <iostream>
#include <fstream>
#include <cstdlib>
#include <iomanip>

using namespace std;

class WeatherReport
{
private:
	int dayOfMonth, highTemp, lowTemp, amountRain, amountSnow;

public:
	WeatherReport();
	friend WeatherReport Summary(WeatherReport day, WeatherReport month);
	void setDay(int day);
	void setHighTemp(int temp);
	void setLowTemp(int temp);
	void setRainAmount(int rain);
	void setSnowAmount(int snow);
	void display();
	void printHeader();
};

/*
	Constructor for Weather Report - Set All Varibale to Default Values
*/
WeatherReport::WeatherReport()
{
	dayOfMonth = 99;
	highTemp = -9999;
	lowTemp = 9999;
	amountRain = 0;
	amountSnow = 0;
}

/*
	This friend class will take two WeatherReport objects
	and compare the data. It will determine which object
	has the higher/lower temperature and record it appropriately.
	This class will also accumulate all precipitation.
*/
WeatherReport Summary(WeatherReport day, WeatherReport month)
{
	WeatherReport thisSummary;
	thisSummary = month;

	if (day.highTemp > month.highTemp)
		thisSummary.highTemp = day.highTemp;

	if (day.lowTemp < month.lowTemp)
		thisSummary.lowTemp = day.lowTemp; 

	thisSummary.amountRain += day.amountRain;
	thisSummary.amountSnow += day.amountSnow;

	return thisSummary;
}

/*
	This simply prints a nice looking header at the top of the program	
*/
void WeatherReport::printHeader()
{
	cout << endl <<
		"\t#     #                                          " << endl <<
		"\t#  #  # ######   ##   ##### #    # ###### #####  " << endl <<
		"\t#  #  # #       #  #    #   #    # #      #    # " << endl <<
		"\t#  #  # #####  #    #   #   ###### #####  #    # " << endl <<
		"\t#  #  # #      ######   #   #    # #      #####  " << endl <<
		"\t#  #  # #      #    #   #   #    # #      #   #  " << endl <<
		"\t ## ##  ###### #    #   #   #    # ###### #    #  " << endl << endl <<
		"\t################################################" << endl <<
		"\t Weather Reporting Project    -    Corey McCown" << endl <<
		"\t################################################" << endl << endl;
}

/*
	This function is to display weather data
	If the day is 99, that means the day was never set. This is only used if it is for the end of month weather report
	If the day is not 99, that means it is a specific day. This will display a report for that day: High, Low, Rain and Snow Amounts
*/
void WeatherReport::display()
{
	if (dayOfMonth == 99)
		cout << endl << "--- Monthly Report: ---" << endl << "Highest Temperature: " << highTemp << static_cast<char>(248) << endl << "Lowest Temperature: " << lowTemp << static_cast<char>(248) << endl << "Total Rain: " << amountRain << "in." << endl << "Total Snow: " << amountSnow << "in." << endl << "--- End Monthly Report ---" << endl;
	else
		cout << "(Report) Day: " << dayOfMonth << " - High: " << highTemp << static_cast<char>(248) << " - Low: " << lowTemp << static_cast<char>(248) << " - Rain: " << amountRain << "in. - Snow " << amountSnow << " in." << endl;
}

/*
	The following are methods to change values within the class
*/
void WeatherReport::setDay(int day)
{
	dayOfMonth = day;
}

void WeatherReport::setHighTemp(int temp)
{
	highTemp = temp;
}

void WeatherReport::setLowTemp(int temp)
{
	lowTemp = temp;
}

void WeatherReport::setRainAmount(int rain)
{
	amountRain = rain;
}

void WeatherReport::setSnowAmount(int snow)
{
	amountSnow = snow;
}

int main()
{
	//Loads the weather.txt data file, assigns it to weatherdata
	std::fstream weatherdata("weather.txt", std::ios_base::in);

	//Create variables for each weather data
	int day, high, low, rain, snow;
	//Create an iterator variable
	int i = 0;

	//Create an array to store all data
	WeatherReport weather[30];
	//Create a WeatherReport for the end of month 
	WeatherReport weatherSummary;

	//Display the fancy header
	weatherSummary.printHeader();
	cout << "--- Begin Daily Reports: ---" << endl;

	//Iterate the weatherdata file...
	while (weatherdata >> day >> high >> low >> rain >> snow){
		//Set the weather data on this object
		weather[i].setDay(day);
		weather[i].setHighTemp(high);
		weather[i].setLowTemp(low);
		weather[i].setRainAmount(rain);
		weather[i].setSnowAmount(snow);
		weather[i].display();

		//Send this off to the friend function for comparison/updating
		weatherSummary = Summary(weather[i], weatherSummary);

		//Iterate the i variable
		i++;
	}

	//Display the next heading and the monthly report
	cout << "--- End Daily Reports ---" << endl;
	cout << endl << "################################################" << endl;
	weatherSummary.display();
	cout << endl << endl;

	//Terminate the program
	system("Pause");
	cout << "Goodbye.";
	return 0;
}

/* Weather Data, save as weather.txt

01 78 32 3 0
02 76 43 1 0
03 68 29 0 0
04 69 35 1 0
05 56 44 0 0
06 52 24 0 0
07 65 35 0 0
08 73 33 0 0
09 55 22 0 0
10 48 40 1 0
11 51 41 0 0
12 55 34 1 0
13 46 35 1 0
14 42 32 1 0
15 41 31 2 0
16 39 30 0 1
17 38 29 0 1
18 36 26 0 2
19 37 27 0 1
20 41 29 0 0
21 43 34 0 0
22 48 36 1 0
23 49 38 0 0
24 50 40 0 0
25 52 43 0 0
26 50 38 1 0
27 54 39 0 0
28 51 37 0 0
29 47 44 1 0
30 91 45 0 0

*/
