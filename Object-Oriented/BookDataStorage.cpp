#include <string>
#include <iostream>

using namespace std;

//Define all variables and methods that the Book class needs
class Book
{
public:
	std::string title, author;
	void setTitle(std::string thisTitle);
	void setAuthor(std::string thisAuthor);
	void getTitle();
	void getAuthor();
	void displayHeader();
};

//Define all variables and methods that the Fiction class needs
//This class derives from Book, meaning all of it's contents are accessible here
class Fiction : public Book
{
public:
	int readingLevel;
	void setReadingLevel(int thisReadingLevel);
	void getReadingLevel();
	void display();
};

//Define all variables and methods that the NonFiction class needs
//This class derives from Book, meaning all of it's contents are accessible here
class NonFiction : public Book
{
public:
	int pageNumber;
	void setPageNumber(int thisPageNumber);
	void getPageNumber(); 
	void display();
};

//Now define all of the methods, and make them do the appropriate actions
void Book::setTitle(std::string thisTitle)
{
	title = thisTitle;
}

void Book::setAuthor(std::string thisAuthor)
{
	author = thisAuthor;
}

void Book::getTitle()
{
	cout << title;
}

void Book::getAuthor()
{
	cout << author;
}

void Book::displayHeader()
{
	cout << "#################################################\n";
	cout << " ____              _      _____        __      \n";
	cout << "|  _ \\            | |    |_   _|      / _|     \n";
	cout << "| |_) | ___   ___ | | __   | |  _ __ | |_ ___  \n";
	cout << "|  _ < / _ \\ / _ \\| |/ /   | | | '_ \\|  _/ _ \\ \n";
	cout << "| |_) | (_) | (_) |   <   _| |_| | | | || (_) |\n";
	cout << "|____/ \\___/ \\___/|_|\\_\\ |_____|_| |_|_| \\___/ o\n\n";
	cout << "######## Project 5 -------- Corey McCown ########\n";
	cout << "#################################################\n\n";

}

void Fiction::setReadingLevel(int thisReadingLevel)
{
	readingLevel = thisReadingLevel;
}

void Fiction::getReadingLevel()
{
	cout << std::to_string(readingLevel);
}

void Fiction::display()
{
	cout << "FICTION BOOK:\n\t- Title: ";
	getTitle();
	cout << "\n\t- Author: ";
	getAuthor();
	cout << "\n\t- Reading Level: ";
	getReadingLevel();
	cout << endl << endl;
}

void NonFiction::setPageNumber(int thisPageNumber)
{
	pageNumber = thisPageNumber;
}

void NonFiction::getPageNumber()
{
	cout << std::to_string(pageNumber);
}

void NonFiction::display()
{
	cout << "NON-FICTION BOOK:\n\t- Title: ";
	getTitle();
	cout << "\n\t- Author: ";
	getAuthor();
	cout << "\n\t- Number of Pages: ";
	getPageNumber();
	cout << endl << endl;
}

int main()
{
	
	//Instantiate one of each class types
	NonFiction historyBook;
	Fiction storyBook;
	Book genBook;

	//Display Header
	genBook.displayHeader();

	//Set all information
	historyBook.setTitle("On the Origin of Species");
	historyBook.setAuthor("Charles Darwin");
	historyBook.setPageNumber(502);

	storyBook.setTitle("Harry Potter and the Sorcerer's Stone");
	storyBook.setAuthor("J.K. Rowling");
	storyBook.setReadingLevel(6);

	genBook.setAuthor("John Doe");
	genBook.setTitle("A Book About Me");

	//Display General Book Information, since there isn't a display method
	cout << "GENERAL BOOK INFORMATION:\n\t- Title: ";
	genBook.getTitle();
	cout << "\n\t- Author: ";
	genBook.getAuthor();
	cout << endl << endl;

	//Display history and story book information
	historyBook.display();
	storyBook.display();
	
	//Terminate the program
	system("Pause");
	cout << "Goodbye.";
	return 0;
}
