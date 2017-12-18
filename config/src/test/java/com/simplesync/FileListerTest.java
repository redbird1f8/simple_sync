package com.simplesync;

import com.simplesync.methods.ChooseForeign;
import com.simplesync.methods.ChooseLocal;
import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FileListerTest {

    @Test
    public void testAccordanceWithListIterator() throws Exception {

        List<FileEntry> fileEntries = Arrays.asList(
                new FileEntry(resourceToPath("sample1.txt"), new Rule(new ChooseForeign(), true)),
                new FileEntry(resourceToPath("sample2.txt"), new Rule(new ChooseLocal(), false)),
                new FileEntry(resourceToPath("sample3.txt"), new Rule(new ChooseForeign(), false))
        );
        FileLister fileLister = new FileLister(fileEntries);

        Iterator<FileEntry> fileEntryIterator = fileEntries.iterator();
        Iterator<FileEntry> fileListerIterator = fileLister.getIterator();

        for (int i = 0; i < 3; i++) {
            Assert.assertEquals(fileListerIterator.next(), fileEntryIterator.next());
        }
        Assert.assertFalse(fileListerIterator.hasNext());
    }

    private Path resourceToPath(String resourceName) throws URISyntaxException {
        return Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
    }
}
