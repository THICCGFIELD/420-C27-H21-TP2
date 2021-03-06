/*
Objectif: Modal qui sert à ajouter un objet à l'inventaire
  Auteur: Marc-Antoine Dubois
  Date: 2021-05-17 Session H2021
 */

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Objects;

public class ViewAjoutInventaire extends JDialog {

    JDialog dialog; //Interface du modal

    JLabel lblNom; //Label du nom
    JLabel lblnumSerie; //Label du numéro de serie
    JLabel lblCategorie; // Label de la catégorie à sélectionner
    JLabel lblPrix; // Label du prix
    JLabel lblDateAchat; // Label de la date d'achat
    JLabel lblDescription; // Label de la description
    JTextField txfNom; // Input pour le nom de l'objet
    JTextField txfNoSerie; // Input pour le numéro de serie
    JTextField txfPrix; // Input pour le prix de l'objet
    JTextArea txaDescription; // Input pour la description de l'objet
    JComboBox<String> cmbCategorie; // Input pour la catégorie de l'objet
    JDateChooser dateAchat; // Input pour la date d'achat

    JButton btnAjouter; // Bouton pour ajouter l'item à la table d'inventaire
    JButton btnAnnuler; // Bouton pour annuler l'ajout d'item
    JScrollPane scrollPaneDescription; //Scrollpane pour scroll dans le texte de la description

    Inventaire nouveauObjet = new Inventaire(); // nouvelle objet à ajouter à l'inventaire

    public ViewAjoutInventaire(){

        //Création des labels
        lblNom = new JLabel("*Nom:");
        lblnumSerie= new JLabel("No série:");
        lblCategorie = new JLabel("Catégorie:");
        lblPrix = new JLabel("*Prix:");
        lblDateAchat = new JLabel("*Date achat:");
        lblDescription = new JLabel("Description:");

        //Création des textfields
        UIManager.put("TextField.inactiveBackground", Color.WHITE);
        txfNom = new JTextField();
        txfNoSerie = new JTextField();
        txfPrix = new JTextField();
        txfPrix.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int numPoint = 0;
                String currentText = txfPrix.getText() + e.getKeyChar(); //le texte avec la nouvelle valeur appuyé
                for (int i = 0; i < currentText.length(); i++){
                    if(currentText.charAt(i) == '.'){
                        numPoint++;
                    }
                }
                if(((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '.' || e.getKeyChar() == KeyEvent.VK_BACK_SPACE) && numPoint<=1){
                    txfPrix.setEditable(true);
                }
                else{
                    txfPrix.setEditable(false);
                    //Mettre le border la couleur par défaut pour donner impression que le textfield n'est pas disabled
                    txfPrix.setBorder(new LineBorder(new java.awt.Color(122,138,153)));
                }
            }
        });

        txaDescription = new JTextArea();

        //Création du combobox
        cmbCategorie = new JComboBox<>(new String[]{"Caméra", "Accessoires", "Jeux", "Véhicule", "Divers"});

        //Création du date picker
        dateAchat = new JDateChooser();
        dateAchat.setDateFormatString("dd MMM yyyy");

        //Création des boutons
        btnAjouter = new JButton("Ajouter");
        btnAjouter.addActionListener(e->btnAjouterAction());
        btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(e->btnAnnulerAction());

        // Création scrollPane pour textArea
        scrollPaneDescription = new JScrollPane(txaDescription);

        //Création interface
        initialiserInterface();
    }

    /**
     * Création d'un nouvel objet de type Inventaire
     */
    private void btnAjouterAction() {

        DecimalFormat df = new DecimalFormat("#.##"); // pattern pour round deux chiffre après virgule

        if(!txfPrix.getText().equals("") && !txfNom.getText().equals("") && dateAchat.getDate() != null){
            double prix =  Double.parseDouble(df.format(Double.parseDouble(txfPrix.getText()))); // prix de l'objet arondi en double

            nouveauObjet.setNom(txfNom.getText());
            nouveauObjet.setNumSerie(txfNoSerie.getText());
            nouveauObjet.setCategorie(Objects.requireNonNull(cmbCategorie.getSelectedItem()).toString());
            nouveauObjet.setPrix(prix);
            nouveauObjet.setDateAchat(dateAchat.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            nouveauObjet.setDescription(txaDescription.getText());

            dialog.dispose();
        }
        else{
            JOptionPane.showMessageDialog(dialog," Erreur de donnée");
        }
    }

    /**
     * Ferme le modal
     */
    private void btnAnnulerAction() {
        dialog.dispose();
    }

    /**
     * Retourne l'objet qui vient d'être créer
     * @return Objet de type Inventaire
     */
    public Inventaire getNouveauObjet(){
        return nouveauObjet;
    }

    /**
     * Initialise l'interface
     */
    private void initialiserInterface(){
        // Création du modal/frame
        dialog = new JDialog((JDialog)null, "Ajout inventaire", true);
        dialog.setSize(new Dimension(600,350));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Création du panel pour l'inventaire
        GridBagLayout gbl = new GridBagLayout();
        JPanel pnlInventaire = new JPanel(gbl);
        GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.insets = new Insets(5,10,0,10);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 0;
        pnlInventaire.add(lblNom, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 0;
        pnlInventaire.add(txfNom, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 1;
        pnlInventaire.add(lblnumSerie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 1;
        pnlInventaire.add(txfNoSerie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 2;
        pnlInventaire.add(lblCategorie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 2;
        pnlInventaire.add(cmbCategorie, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 3;
        pnlInventaire.add(lblPrix, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 3;
        pnlInventaire.add(txfPrix, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 4;
        pnlInventaire.add(lblDateAchat, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.5;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 4;
        pnlInventaire.add(dateAchat, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridx = 0;
        mainConstraints.gridy = 5;
        pnlInventaire.add(lblDescription, mainConstraints);

        mainConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainConstraints.weightx = 0.0;
        mainConstraints.gridwidth = 3;
        mainConstraints.gridx = 1;
        mainConstraints.gridy = 5;
        mainConstraints.ipady = 100;
        pnlInventaire.add(scrollPaneDescription, mainConstraints);

        //Création Panel buttons
        JPanel pnlButton = new JPanel(new FlowLayout());
        pnlButton.add(btnAjouter);
        pnlButton.add(btnAnnuler);

        //Création du panel principal
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.Y_AXIS));
        pnlMain.add(pnlInventaire);
        pnlMain.add(pnlButton);

        dialog.add(pnlMain);
        dialog.setVisible(true);
    }

}
