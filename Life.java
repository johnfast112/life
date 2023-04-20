import java.util.Arrays;
import java.util.Scanner;

class Life{

	public static Scanner input = new Scanner(System.in);

	public static boolean[][] _grid;
	public static boolean settingGrid = true;

	public static void main(String args[]){
		System.out.println("Starting Program");
		_grid = setGridSize();

		printGrid();
		while (settingGrid) {
			setCells();
		}

		System.out.println("How many times would you like to simulate life?");
		int gens = input.nextInt();
		for (int i=0;i<gens;i++){
			simulateLife();
		}
	}


	public static boolean[][] setGridSize(){	//Method for setting grid size
							//defaults
		int height = 10;
		int column = 10;

		System.out.println("Setting grid size");
		System.out.println("Input grid Width.");
		try{
			column = input.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid size. Defaulting to 10.");
			input.next();
		}
		System.out.println("Input grid Height.");
		try{
			height = input.nextInt();
		} catch (Exception e) {
			System.out.println("Invalid size. Defaulting to 10.");
			input.next();
		}

		boolean[][] grid = new boolean[column][height];
		System.out.println("Width: " + column + ". Height: " + height + ".");
		System.out.println("Confirm. Width: " + grid.length + ". Height: " + grid[0].length + ".");
		return grid;
	}

	public static void printGrid(){	//Method for printing grid to screen
					//Printing with borders
		for (int i=0; i<_grid.length*2;i++) {
			System.out.print('-');
		}
		System.out.print("\n");

		int row=0;
		for (boolean[] i:_grid){
			for (int column=0; column<_grid[0].length;column++){
				System.out.print(boolToNumeral(i[column])+" ");
			}
			System.out.print("\t");
			for (int column=0; column<_grid[0].length;column++){
				System.out.print("{"+row+","+column+"}=" + _grid[row][column] + " ");
			}
			System.out.print("\n");
			row++;
		}


		for (int i=0; i<_grid.length*2;i++) {
			System.out.print('-');
		}
		System.out.print("\n");
	}


	public static boolean setCells(){	//Method for setting cell states
		boolean validIn = false;
		System.out.println("Setting cell states.");
		System.out.println("Please select a cell. Format {x,y} with curly brackets. If you want to select multiple cells at a time, seperate them with a space. When you are done, enter an invalid cell to be prompted to start the simulation.");
		String cell = input.next();

		if (cell.length()!=5||cell.charAt(0)!='{'||cell.charAt(2)!=','||cell.charAt(4)!='}'||!Character.isDigit(cell.charAt(1))||!Character.isDigit(cell.charAt(3))){
			System.out.println("Invalid cell. Are you still setting up the grid? y/n");
			while (!validIn) {
				String userIn = input.next();
				if (userIn.equals("y")) {
					settingGrid = true;
					validIn=true;
				} else if (userIn.equals("n")){
					settingGrid = false;
					validIn=true;
				} else {
					System.out.println("Invalid response. Please respond with y/n");
					validIn=false;
				}
			}
		} else {
			int x = Integer.parseInt(String.valueOf(cell.charAt(1)));
			int y = Integer.parseInt(String.valueOf(cell.charAt(3)));
			_grid[x][y] = !_grid[x][y];
		}
		printGrid();

		return true;
	}

	public static void simulateLife() {	//Rewrite this better NESTER
		int cellColumn = 0;
		boolean[][] _gridTmp = new boolean[_grid.length][_grid[0].length];
		for (boolean[] column:_grid) {
			int cellRow = 0;
			for (boolean cell:column) {
				int neighbors=0;
				int[] range = new int[] {-1,0,1};
				String cellInQuestion = "["+cellColumn+"]["+cellRow+"]";
				boolean cellState = _grid[cellColumn][cellRow];
				System.out.println("\nChecking: "+cellInQuestion+"="+cellState); 


				for (int i:range) {
					//String cellCheckAgainst = "["+(cellColumn+i)+"]["+(cellRow+i)+"]="+cellState;
					//System.out.print("Checking against: "+cellCheckAgainst);

					try {
						System.out.print("Checking against: ["+cellColumn+"]["+(cellRow+i)+"]");
						if (_grid[cellColumn+0][cellRow+i]) {
							System.out.print("=1. ");
							neighbors++;
						} else {
							System.out.print("=0. ");
						}
					} catch (ArrayIndexOutOfBoundsException exception) {
						System.out.print("=OOB. ");
					}
					if (i==0&&cellState){System.out.print("Removed");neighbors--;}
					try {
						System.out.print("Checking against: ["+(cellColumn+1)+"]["+(cellRow+i)+"]");
						if (_grid[cellColumn+1][cellRow+i]) {
							System.out.print("=1. ");
							neighbors++;
						} else {
							System.out.print("=0. ");
						}
					} catch (ArrayIndexOutOfBoundsException exception) {
						System.out.print("=OOB. ");
					}
					try {
						System.out.print("Checking against: ["+(cellColumn-1)+"]["+(cellRow+i)+"]");
						if (_grid[cellColumn+-1][cellRow+i]) {
							System.out.print("=1. ");
							neighbors++;
						} else {
							System.out.print("=0. ");
						}
					} catch (ArrayIndexOutOfBoundsException exception) {
						System.out.print("=OOB. ");
					}
				}


				System.out.print("Neighbors: " + neighbors);
				if ((neighbors<=1 || neighbors>=4) && cellState) {
					System.out.print("\nCell dies");
					_gridTmp[cellColumn][cellRow] = false;
				} else if (neighbors==3 && _grid[cellColumn][cellRow]==false){
					System.out.print("\nCell populate");
					_gridTmp[cellColumn][cellRow]=true;
				} else if (cellState){
					System.out.print("\nCell lives");
					_gridTmp[cellColumn][cellRow]=_grid[cellColumn][cellRow];
				}
				System.out.print("\n");
				cellRow++;
			}
			cellColumn++;
		}
		_grid = _gridTmp;
		printGrid();
	}

	public static int boolToNumeral (final Boolean input) {	//Method for printing true:fasle as 1:0. Also return 0 when null/edge of grid
		if (input == null) {
			return 0;
		} else {
			return input.booleanValue() ? 1 : 0;
		}
	}


}
