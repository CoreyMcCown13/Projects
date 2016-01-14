#include <iostream>
#include <stdlib.h>
#include <string>
#include <sstream>
#include <iomanip>

using namespace std;

class Customer
{
public:
	void setCustomerId(int thisId);
	void setCustomerName(string firstName, string lastName);
	void setCustomerCredit(int creditAmount);
	void showCustomerData(void);

private:
	int customer_id;
	std::string last_name;
	std::string first_name;
	int credit_limit;
};

void Customer::setCustomerId(int thisId)
{
	customer_id = thisId;
}

void Customer::setCustomerName(string firstName, string lastName)
{
	first_name = firstName;
	last_name = lastName;
}

void Customer::setCustomerCredit(int creditAmount)
{
	while (creditAmount > 10000){
		cout << "Credit limit must be under $10000. Enter a new value: $";
		cin >> creditAmount;
	}
	credit_limit = creditAmount;
}

void Customer::showCustomerData(void)
{
	cout << "(Customer ID: " << customer_id << ") " << last_name << ", " << first_name << ": Credit = $" << credit_limit << endl;
}

int main()
{
	//Print heading
	cout << "############################################" << endl;
	cout << "#         Displaying All Customers         #" << endl;
	cout << "############################################" << endl << endl;
	
	//Declaring all 5 customers in the array
	Customer customers[5];

	//Instantiating all customers and assigning their data
	customers[0].setCustomerId(1);
	customers[0].setCustomerName("Corey", "McCown");
	customers[0].setCustomerCredit(9000);
	
	customers[1].setCustomerId(2);
	customers[1].setCustomerName("Jimmy", "Neutron");
	customers[1].setCustomerCredit(400);
	
	customers[2].setCustomerId(3);
	customers[2].setCustomerName("Yolo", "Swaggins");
	customers[2].setCustomerCredit(4500);
	
	customers[3].setCustomerId(4);
	customers[3].setCustomerName("Johnny", "Bravo");
	customers[3].setCustomerCredit(100);

	customers[4].setCustomerId(5);
	customers[4].setCustomerName("Briggin", "DaPayne");
	customers[4].setCustomerCredit(8600);

	//Iterating the customers and displaying their data
	for (int i = 0; i < 5; i++)
		customers[i].showCustomerData();

	//Displaying a linebreak... for style!
	cout << endl;

	system("pause");
	return 0;
}
