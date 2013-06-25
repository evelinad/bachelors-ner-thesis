/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import annotators.AnnNamedEntity;
import annotators.NamedEntities;
import annotators.StanfordNerAnnotator;
import edu.stanford.nlp.ie.NERClassifierCombiner;
import edu.stanford.nlp.ie.regexp.NumberSequenceClassifier;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import org.apache.commons.io.FileUtils;


/**
 *
 * @author vlad
 */
public class VisualNer extends javax.swing.JFrame {

    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            VisualNer.class.getName());
    /**
     * Creates new form VisuaNer
     */
    public static final String MODEL_5_CLASS = "D:/Work/NLP/corpuses/ms_academic/models/5-class.ser.gz";
    private String _textFilename;
    private File _textFile;
    private String _rawText;
    private String _annotatedText;
    private StanfordNerAnnotator _annotator;

    public VisualNer() {

        initComponents();
        _annotator = initializeStanfordAnnotator();
        nerTextPane.setContentType("text/html");

    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        nerTextPane = new javax.swing.JTextPane();
        annotateEntitiesButton = new javax.swing.JButton();
        loadTextFileButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane2.setViewportView(nerTextPane);

        annotateEntitiesButton.setText("Identifica Entitati");
        annotateEntitiesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annotateEntitiesButtonActionPerformed(evt);
            }
        });

        loadTextFileButton.setText("Incarca Fisier Text");
        loadTextFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadTextFileButtonActionPerformed(evt);
            }
        });

        jLabel5.setText("Adaugati text mai jos");

        jPanel2.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(loadTextFileButton)
                        .addGap(327, 327, 327))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(annotateEntitiesButton)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadTextFileButton)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(annotateEntitiesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(197, 197, 197))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(36, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void annotateEntitiesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annotateEntitiesButtonActionPerformed

        _rawText = nerTextPane.getText();
        NerTask task = new NerTask(_rawText, _annotator, this);
        task.execute();
    }//GEN-LAST:event_annotateEntitiesButtonActionPerformed

    private void loadTextFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTextFileButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new TextFileFilter());
        //fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fc.setCurrentDirectory(new File("D:/Work/NLP/corpuses/ms_academic/train-io"));
        int returnVal = fc.showDialog(VisualNer.this, "Deschide");
        log.debug("return val" + returnVal);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            //This is where a real application would open the file.
            log.info("Opening: " + file.getName() + ".");
            _textFilename = file.getAbsolutePath();
            _textFile = file;
            loadText();
        } else {
            System.out.println("Open command cancelled by user.");
        }

    }//GEN-LAST:event_loadTextFileButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;


                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VisualNer.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VisualNer.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VisualNer.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VisualNer.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            log.error(ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VisualNer().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton annotateEntitiesButton;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton loadTextFileButton;
    private javax.swing.JTextPane nerTextPane;
    // End of variables declaration//GEN-END:variables

    private void loadText() {
        try {
            _rawText = FileUtils.readFileToString(_textFile, "UTF-8");
        } catch (IOException ex) {
            log.error(ex);
        }

        getNerTextPane().setText(_rawText);
        annotateEntitiesButton.setEnabled(true);
    }

    private StanfordNerAnnotator initializeStanfordAnnotator() {
        log.info("Initializing Stanford Annotator");
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
        props.put("ner.model", "D:/Work/NLP/corpuses/ms_academic/models/5-class.ser.gz");
        props.put(NERClassifierCombiner.APPLY_NUMERIC_CLASSIFIERS_PROPERTY, "false");
        props.put(NumberSequenceClassifier.USE_SUTIME_PROPERTY, "false");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        return new StanfordNerAnnotator(pipeline);
    }

    /**
     * @return the _annotatedText
     */
    public String getAnnotatedText() {
        return _annotatedText;
    }

    /**
     * @param annotatedText the _annotatedText to set
     */
    public void setAnnotatedText(String annotatedText) {
        this._annotatedText = annotatedText;
    }

    /**
     * @return the nerTextPane
     */
    public javax.swing.JTextPane getNerTextPane() {
        return nerTextPane;
    }
}
class NerTask extends SwingWorker<String, Integer> {

    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            NerTask.class.getName());
    private String _text;
    private VisualNer _visualNer;
    private StanfordNerAnnotator _anntoator;

    NerTask(String text, StanfordNerAnnotator annotator, VisualNer visualNer) {
        _text = text;
        _visualNer = visualNer;
        _anntoator = annotator;

    }

    @Override
    public String doInBackground() {

        NamedEntities entities = _anntoator.annotateText(_text);

        Set<String> categories = entities.getAllCategories();
        log.info("Categories count " + categories.size());
        int personsCount = 0;
        int orgCount = 0;
        int locationsCount = 0;
        int miscCount = 0;
        
        List<AnnNamedEntity> personEntities = entities.getEntitiesByCategory("PERSON");
        List<AnnNamedEntity> orgEntities = entities.getEntitiesByCategory("ORGANIZATION");
        List<AnnNamedEntity> locationEntity = entities.getEntitiesByCategory("LOCATION");
        List

        String annotatedText = annotateHtml(entities);
        log.info("Ann text " + annotatedText);
        _visualNer.setAnnotatedText(annotatedText);
        _visualNer.getNerTextPane().setText(annotatedText);
        return annotatedText;

    }

    private String annotateHtml(NamedEntities entities) {
        List<AnnNamedEntity> sortedEntities = entities.getAllEntitiesInSortedOrder();
        StringBuilder sb = new StringBuilder();
        log.info("Count " + sortedEntities.size());

        int currentIndexInText = 0;
        int count = 0;

        log.info("Text length " + _text.length());
        for (AnnNamedEntity entity : sortedEntities) {
            int startIndex = entity.getStartIndex();
            int endIndex = entity.getEndIndex();
            log.info(startIndex);
            log.info(endIndex);
          //  log.info(entity.getNamedEntityText());
          
            sb.append(_text.substring(currentIndexInText, startIndex)).
                    append("<span style='background-color: #ffffcc;'>").
                    append(_text.substring(startIndex, endIndex)).
                    append("</span>");
          //  log.info(count++);
           // log.info(sortedEntities.size());
            currentIndexInText = endIndex;
        }
        
        sb.append(_text.substring(currentIndexInText));

        return sb.toString();
    }
}
