# WordStat and CMDb Applications of `PriorityQueues` and `HashTables`

## Overview

This Java project comprises several tools and utilities for text analysis and movie database management. It features the WordStat application for analyzing word frequencies within text, the CMDb (Case Movie Database) for managing user profiles and favorite movies, and supporting utilities including a Hash Table for efficient data storage and a Priority Queue for ordered data management.

## Components

### WordStat
- **Functionality**: Analyzes text to compute statistics like word frequencies, ranks, and collocations.
- **Use Cases**: Useful for linguistic research, SEO optimization, and understanding text composition.

### CMDb
- **Functionality**: Manages user profiles, including movie ratings and group dynamics.
- **Use Cases**: Ideal for social movie recommendation systems, user engagement analysis, and community-driven movie discussions.

### Supporting Utilities
- **Hash Table**: Implements separate chaining for collision resolution, offering efficient data storage and retrieval.
- **Priority Queue**: Facilitates ordered management of data based on custom priority rules.

### Additional Components
- **CMDbProfile**: Represents user profiles, handling movie ratings and preferences.
- **Tester.java**: Provides JUnit testing for the project, ensuring reliability and correctness of implementations.

## Prerequisites
- Java Development Kit (JDK) 11 or higher.

## Setup and Execution

1. **Compilation**: Compile the Java files using your preferred development environment or the command line.
2. **Running WordStat**:
    - For file input: `java WordStat "path/to/textfile.txt"`
    - For direct string input: `java WordStat "string1 string2 string3 ..."`
3. **Running CMDb**: Simply initiate the CMDb application with `java CMDbGroup`, and follow the interactive prompts or integrate it into a larger project.

## Features and Usage

### WordStat
- Obtain word frequency reports.
- Identify the most and least common words.
- Analyze collocations around a base word.

### CMDb
- Register user profiles and manage movie ratings.
- Group users and analyze collective movie preferences.
- Recommend movies based on group favorites or random selections.

### Hash Table & Priority Queue
- Efficiently store and manage large sets of data with customizable handling for collisions (Hash Table) and data priorities (Priority Queue).

## Development and Contribution

Contributions are welcome. Please adhere to conventional coding standards and provide tests for new features or bug fixes. Use pull requests for submissions.

## Testing

Utilize the provided `MainTester.java` and `Tester.java` for JUnit testing, ensuring that new contributions do not break existing functionalities.

You can run them as follows:
```bash
java MainTester
java Tester
```

They will use sample testing files and data, such as `TestTextFile.txt` or `TestTextFile1.txt`, etc. to test all functions and methods of the Java classes and programs.

## License

This project is open-source, available for modification and distribution under standard open-source licenses.

## Contact

For bug reports, feature requests, or contributions, please open an issue or pull request on the project's repository.

---

Created with ❤️ in 2023 by [Son Nguyen](https://github.com/hoangsonww).
