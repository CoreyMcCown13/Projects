#include <iostream>
#include <stdlib.h>
#include <string>
#include <sstream>
#include <iomanip>

using namespace std;

class Person
{
public:
	void display(void);
	Person();
	Person(std::string thisID, std::string firstName, std::string lastName);

private:
	std::string id;
	std::string last_name;
	std::string first_name;
};

Person::Person()
{
	last_name = "?";
	first_name = "?";
	id = "X";
}

Person::Person(std::string thisID, std::string firstName, std::string lastName)
{
	id = thisID;
	last_name = lastName;
	first_name = firstName;
}

void Person::display(void)
{
	cout << "ID: " << id << " - " << last_name << ", " << first_name;
}

class StudentGrade
{
public:
	StudentGrade(std::string stuID, std::string firstName, std::string lastName, int testScore);
	StudentGrade(std::string stuID, std::string firstName, std::string lastName, int testScore, int testPoints);
	void display();
	std::string letter_grade;
	Person thisStudent;

private:
	void calculateGrade();
	int test_score;
	int test_points;
	
};

StudentGrade::StudentGrade(std::string stuID, std::string firstName, std::string lastName, int testScore)
{
	thisStudent = Person(stuID, firstName, lastName);
	test_score = testScore;
	test_points = 100;
	calculateGrade();
}

StudentGrade::StudentGrade(std::string stuID, std::string firstName, std::string lastName, int testScore, int testPoints)
{
	thisStudent = Person(stuID, firstName, lastName);
	test_score = testScore;
	test_points = testPoints;
	calculateGrade();
}

void StudentGrade::calculateGrade()
{
	//Cast the test_score and test_points variables to a double
	double student_score = (double)test_score;
	double total_score = (double)test_points;

	//Divide the double to get test score
	double thisGrade = (double)(student_score / total_score);

	//Multiply that result by 100 to get a percent
	thisGrade = thisGrade * 100;

	if (thisGrade >= 90)
		letter_grade = "A";
	if (thisGrade >= 80 && thisGrade < 90)
		letter_grade = "B";
	if (thisGrade >= 70 && thisGrade < 80)
		letter_grade = "C";
	if (thisGrade >= 60 && thisGrade < 70)
		letter_grade = "D";
	if (thisGrade < 60)
		letter_grade = "F";
		
}

void StudentGrade::display()
{
	thisStudent.display();
	cout << " Grade: " << letter_grade << " (" << test_score << "/" << test_points << ")" << endl;
}

int main()
{
	//Print heading
	cout << "############################################" << endl;
	cout << "#         Displaying All Students          #" << endl;
	cout << "############################################" << endl << endl;
	
	//Instantiating all students and assigning their data
	StudentGrade student1("1", "Great", "Student", 9, 10); //Constructor with max test score
	StudentGrade student2("2", "Connor", "Isastudent", 83); //Constructor without max test score (default: 100)
	StudentGrade student3("3", "James", "Lulzer", 75, 105);
	StudentGrade student4("4", "Ricky", "Henry", 67);
	StudentGrade student5("5", "Joe", "BeFailing", 45);

	student1.display();
	student2.display();
	student3.display();
	student4.display();
	student5.display();
	

	//Displaying a linebreak... for style!
	cout << endl;

	system("pause");
	return 0;
}
