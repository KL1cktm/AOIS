package minimization;

public class MethodOfMinimization {
    private String logicFormula;
    private MinimizeNormalForm minimizeNormalForm;
    public MethodOfMinimization(String logicFormula) {
        this.logicFormula = logicFormula;
        this.minimizeNormalForm = new MinimizeNormalForm();
        this.minimizeNormalForm.setLogicForm(logicFormula);
    }
    public void changeLogicFormula(String logicFormula) {
        this.minimizeNormalForm.setLogicForm(logicFormula);
    }
    public void createPCNFByCalculatedMethod() {
        System.out.println("\nMINIMIZE PCNF BY CALCULATED METHOD\n");
        this.minimizeNormalForm.minimizePCNF();
    }
    public void createPDNFByCalculatedMethod() {
        System.out.println("\nMINIMIZE PDNF BY CALCULATED METHOD\n");
        this.minimizeNormalForm.minimizePDNF();
    }
    public void createPCNFByTableMethod() {
        System.out.println("\nMINIMIZE PCNF BY TABLE METHOD\n");
        this.minimizeNormalForm.minimizePCNFByTable();
    }
    public void createPDNFByTableMethod() {
        System.out.println("\nMINIMIZE PDNF BY TABLE METHOD\n");
        this.minimizeNormalForm.minimizePDNFByTable();
    }
    public void createPDNFByCarnotMethod() {
        MethodCarnot carnot = new MethodCarnot();
        carnot.setLogicFormula(this.logicFormula);
        System.out.println("\nMINIMIZE PDNF BY CARNOT METHOD\n");
        carnot.minimizePDNF();
    }
    public void createPCNFByCarnotMethod() {
        MethodCarnot carnot = new MethodCarnot();
        carnot.setLogicFormula(this.logicFormula);
        System.out.println("\nMINIMIZE PCNF BY CARNOT METHOD\n");
        carnot.minimizePCNF();
    }
}

